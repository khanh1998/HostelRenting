package org.avengers.capstone.hostelrenting.service.impl;


import com.lowagie.text.DocumentException;
import org.apache.commons.collections.CollectionUtils;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOConfirm;


import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.exception.PreCreationException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.repository.GroupServiceRepository;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.util.BASE64DecodedMultipartFile;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    //    @Value("${azure.storage.contract-template}")
//    private String contractTemplatePath;
    @Value("${azure.storage.contract-font}")
    private String fontPath;

    private ContractRepository contractRepository;
    private GroupServiceRepository groupServiceRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private ModelMapper modelMapper;
    private RenterService renterService;
    private VendorService vendorService;
    private RoomService roomService;
    private GroupServiceService groupServiceService;
    private DealService dealService;
    private BookingService bookingService;
    private FileStorageServiceImp fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageServiceImp fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setGroupServiceRepository(GroupServiceRepository groupServiceRepository) {
        this.groupServiceRepository = groupServiceRepository;
    }

    @Autowired
    public void setGroupServiceService(GroupServiceService groupServiceService) {
        this.groupServiceService = groupServiceService;
    }

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Contract> model = contractRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
    }

    @Override
    public Contract findById(Integer id) {
        checkExist(id);

        return contractRepository.getOne(id);
    }

    @Override
    public Contract create(Contract reqModel) throws PreCreationException {
        Optional<Contract> tempContract = contractRepository.findByVendor_UserIdAndRenter_UserIdAndRoom_RoomId(
                reqModel.getVendor().getUserId(),
                reqModel.getRenter().getUserId(),
                reqModel.getRoom().getRoomId());
        if (tempContract.isPresent())
            throw new GenericException(Contract.class, "Contract has already with ",
                    "contractId", String.valueOf(tempContract.get().getContractId()),
                    "renterId", String.valueOf(tempContract.get().getRenter().getUserId()),
                    "vendorId", String.valueOf(tempContract.get().getVendor().getUserId()));

        int groupId = reqModel.getRoom().getType().getGroup().getGroupId();
        Collection<Integer> reqServiceIds = reqModel.getGroupServices().stream().map(GroupService::getGroupServiceId).collect(Collectors.toList());
        Collection<GroupService> availableServices = groupServiceRepository.findByGroup_GroupIdAndIsActiveIsTrue(groupId);

        validatePreCreate(reqModel, reqServiceIds, availableServices);

        Collection<GroupService> validServices = reqServiceIds.stream().map(id -> groupServiceService.findById(id)).collect(Collectors.toList());
        /* Set groupServices for contract model */
        reqModel.setGroupServices(validServices);
        /* Set room for contract model */
//        reqModel.setRoom(roomService.updateStatus(reqModel.getRoom().getRoomId(), false));
        Contract resModel = contractRepository.save(reqModel);

        // process business after create contract
        resModel = processAfterCreate(resModel);

        return resModel;
    }

    @Override
    public Contract confirm(Contract exModel, ContractDTOConfirm reqDTO) {
        if (exModel.getStatus() == Contract.STATUS.ACTIVATED) {
            throw new GenericException(Contract.class, "cannot be updated", "contractId", String.valueOf(exModel.getContractId()), "status", exModel.getStatus().toString());
        }
        if (exModel.getQrCode().equals(reqDTO.getQrCode())) {
            modelMapper.map(reqDTO, exModel);
            //send mail
            String contractHtml = generateContractHTML(exModel);
            String contractUrl = uploadPDF(contractHtml, String.valueOf(exModel.getContractId()));
            sendMailWithEmbed(contractHtml, exModel.getRenter().getEmail());
            sendMailWithEmbed(contractHtml, exModel.getVendor().getEmail());
            exModel.setContractUrl(contractUrl);
            Contract resModel = contractRepository.save(exModel);
            return resModel;
        }
        throw new GenericException(Contract.class, "qrCode not matched", "contractId", String.valueOf(exModel.getContractId()), "qrCode", exModel.getQrCode().toString());
    }

    /**
     * Get list of contracts by given renter id
     *
     * @param renterId given renter id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByRenterId(Long renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getContracts();
    }

    /**
     * Get list of contracts by given vendor id
     *
     * @param vendorId given vendor id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByVendorId(Long vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getContracts();
    }

    /**
     * @param model
     * @return
     */
    private void validatePreCreate(Contract model, Collection<Integer> reqServiceIds, Collection<GroupService> availableServices) throws PreCreationException {
        int groupId = model.getRoom().getType().getGroup().getGroupId();
        int roomId = model.getRoom().getRoomId();
        boolean isViolated = false;
        String errMsg = null;

        /* Violate if room id is not available for create contract */
        if (!roomService.checkAvailableById(roomId)) {
            errMsg = String.format("Room id={%s} is not available!", roomId);
            isViolated = true;
        }

        /* Violate if any of request services not present in required services */
        Collection<GroupService> requiredViolated = groupServiceRepository.findByGroup_GroupIdAndIsActiveIsTrueAndIsRequiredIsTrueAndGroupServiceIdNotIn(groupId, reqServiceIds);
        if (requiredViolated.size() > 0) {
            errMsg = String.format("Missing some required services - groupServiceId=%s!",
                    Arrays.toString(requiredViolated.stream().map(GroupService::getGroupServiceId).toArray()));
            isViolated = true;
        }

        /* Violate if there is a groupServiceId not match with the groupId */
        Collection notPresent = CollectionUtils.subtract(reqServiceIds, availableServices.stream().map(GroupService::getGroupServiceId).collect(Collectors.toList()));
        if (notPresent.size() > 0) {
            errMsg = String.format("Services are not available - groupServiceId=%s",
                    Arrays.toString(notPresent.toArray()));
            isViolated = true;
        }

        /* Violate if the number of request services is bigger than available services */
        if (reqServiceIds.size() > availableServices.size()) {
            errMsg = String.format("Invaid number of request services. Request services {%s} is bigger than available services {%s}", reqServiceIds.size(), availableServices.size());
            isViolated = true;
        }

        if (isViolated)
            throw new PreCreationException(errMsg);
    }

    private Contract processAfterCreate(Contract resModel) {
        int remainRoom = roomRepository.countByType_TypeIdAndIsAvailableIsTrue(resModel.getRoom().getType().getTypeId());
        if (remainRoom == 0) {
            Collection<Booking> incomingBookings = bookingRepository.findByType_TypeIdAndStatusIs(resModel.getRoom().getType().getTypeId(), Booking.STATUS.INCOMING);
            bookingService.cancelBookings(incomingBookings.stream().map(Booking::getBookingId).collect(Collectors.toList()));
        }
        return resModel;
    }

    private String uploadPDF(String contractHtml, String contractId){
        try {
            BASE64DecodedMultipartFile multipartFile = BASE64DecodedMultipartFile.builder()
                    .fileContent(Utilities.generatePdfFromHtml(contractHtml, fontPath))
                    .fileName(Contract.class.getSimpleName() + Constant.Symbol.UNDERSCORE + contractId + Constant.Extension.PDF)
                    .contentType(Constant.ContentType.PDF)
                    .build();
            return fileStorageService.storeFile(multipartFile).getFileDownloadUri();

        } catch (IOException | DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        //TODO: handle
        return null;
    }

    public String generateContractHTML(Contract model) {
        Map<String, String> contractInfo = new HashMap<>();
        contractInfo.put(Constant.Contract.VENDOR_NAME, model.getVendor().getUsername());
        contractInfo.put(Constant.Contract.VENDOR_YEAR_OF_BIRTH, String.valueOf(model.getVendor().getYearOfBirth()));
        contractInfo.put(Constant.Contract.VENDOR_ID_NUMBER, model.getVendor().getCitizenIdNum());
        contractInfo.put(Constant.Contract.VENDOR_ID_ISSUED_DATE, Utilities.getTimeStrFromMillisecond(model.getVendor().getIdIssuedDate()));
        contractInfo.put(Constant.Contract.VENDOR_ID_ISSUED_LOCATION, model.getVendor().getIdIssuedLocation());
        contractInfo.put(Constant.Contract.VENDOR_HOUSEHOLD_ADDRESS, model.getVendor().getHouseholdAddress());
        contractInfo.put(Constant.Contract.VENDOR_CURRENT_ADDRESS, model.getVendor().getCurrentAddress());
        contractInfo.put(Constant.Contract.VENDOR_PHONE_NUMBER, model.getVendor().getPhone());

        contractInfo.put(Constant.Contract.RENTER_NAME, model.getRenter().getUsername());
        contractInfo.put(Constant.Contract.RENTER_YEAR_OF_BIRTH, String.valueOf(model.getRenter().getYearOfBirth()));
        contractInfo.put(Constant.Contract.RENTER_ID_NUMBER, model.getRenter().getCitizenIdNum());
        contractInfo.put(Constant.Contract.RENTER_ID_ISSUED_DATE, Utilities.getTimeStrFromMillisecond(model.getRenter().getIdIssuedDate()));
        contractInfo.put(Constant.Contract.RENTER_ID_ISSUED_LOCATION, model.getRenter().getIdIssuedLocation());
        contractInfo.put(Constant.Contract.RENTER_HOUSEHOLD_ADDRESS, model.getRenter().getHouseholdAddress());
        contractInfo.put(Constant.Contract.RENTER_CURRENT_ADDRESS, model.getRenter().getCurrentAddress());
        contractInfo.put(Constant.Contract.RENTER_PHONE_NUMBER, model.getRenter().getPhone());
        contractInfo.put(Constant.Contract.RENTER_SCHOOL_NAME, model.getRenter().getSchool().getSchoolName());
        String schoolDistrict = model.getRenter().getSchool().getDistrict().getDistrictName();
        String schoolProvince = model.getRenter().getSchool().getDistrict().getProvince().getProvinceName();

        contractInfo.put(Constant.Contract.RENTER_SCHOOL_ADDRESS, String.format("%s, %s",schoolDistrict, schoolProvince));

//        String templateName = Utilities.getFileNameWithoutExtensionFromPath(contractTemplatePath);
        String contractHtml = Utilities.parseThymeleafTemplate(Constant.Contract.TEMPLATE_NAME, contractInfo);

        return contractHtml;
    }

    public void sendMailWithEmbed(String contractHtml, String receivedMail) {
//        String to = "thanhnv.se@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "avenger.youthhostel@gmail.com";
        final String username = "avenger.youthhostel@gmail.com";
        final String password = "KieuTrongKhanh!$&1";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receivedMail));
            message.setSubject("Hợp đồng thuê nhà");
            message.setContent(contractHtml,"text/html; charset=UTF-8");
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
