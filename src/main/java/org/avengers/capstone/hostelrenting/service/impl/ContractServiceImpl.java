package org.avengers.capstone.hostelrenting.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import org.apache.commons.collections.CollectionUtils;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOConfirm;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.contract.ContractImageDTOCreate;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationContent;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.exception.PreCreationException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.*;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.avengers.capstone.hostelrenting.service.ContractService;
import org.avengers.capstone.hostelrenting.service.GroupServiceService;
import org.avengers.capstone.hostelrenting.service.RoomService;
import org.avengers.capstone.hostelrenting.util.BASE64DecodedMultipartFile;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Value("${azure.storage.contract-font}")
    private String fontPath;

    @Value("${mail.admin.username}")
    private String adminGmailUsername;
    @Value("${mail.admin.password}")
    private String adminGmailPwd;

    @Value("${mail.smtp.auth}")
    private String mailAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String mailStartTlsEnable;

    @Value("${mail.smtp.host}")
    private String mailHost;

    @Value("${mail.smtp.port}")
    private String mailPort;

    @Value("${mail.smtp.debug}")
    private String mailDebug;

    @Value("${mail.smtp.socketFactory.port}")
    private String mailSocketFactoryPort;

    @Value("${mail.smtp.socketFactory.class}")
    private String mailSocketFactoryClass;

    @Value("${mail.smtp.socketFactory.fallback}")
    private String mailSocketFactoryFallback;

    private ContractRepository contractRepository;
    private GroupServiceRepository groupServiceRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private GroupRepository groupRepository;
    private ModelMapper modelMapper;
    private RoomService roomService;
    private GroupServiceService groupServiceService;
    private BookingService bookingService;
    private FileStorageServiceImp fileStorageService;
    private FirebaseService firebaseService;

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

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
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
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
        reqModel.setRoom(roomService.updateStatus(reqModel.getRoom().getRoomId(), false));
        for (ContractImage image : reqModel.getContractImages()) {
            image.setContract(reqModel);
        }
        Contract resModel = contractRepository.save(reqModel);
        // send notification
        sendNotification(resModel,
                Constant.Notification.NEW_CONTRACT,
                Constant.Notification.STATIC_NEW_CONTRACT_MESSAGE,
                resModel.getRenter().getFirebaseToken());

        // process business after create contract
        resModel = processAfterCreate(resModel);

        return resModel;
    }

    @Override
    public Contract updateInactiveContract(Contract exModel, ContractDTOUpdate reqDTO) {
        if (exModel.getStatus() == Contract.STATUS.ACTIVATED) {
            throw new GenericException(Contract.class, "cannot be updated", "contractId", String.valueOf(exModel.getContractId()), "status", exModel.getStatus().toString());
        }
        Integer roomId = reqDTO.getRoomId();
        if (roomId != null) {
            reqDTO.setRoom(roomService.findById(reqDTO.getRoomId()));
            if (!roomRepository.IsExistByVendorIdAndRoomId(exModel.getVendor().getUserId(), reqDTO.getRoomId()))
                throw new GenericException(Room.class, "is not valid with id",
                        "roomId", String.valueOf(reqDTO.getRoomId()));
        } else {
            roomId = exModel.getRoom().getRoomId();
        }
        int groupId = groupRepository.getGroupIdByRoomId(roomId);

        //check whether groupService is valid or not
        if (reqDTO.getGroupServiceIds() != null && !reqDTO.getGroupServiceIds().isEmpty()) {
            reqDTO.setGroupServices(reqDTO.getGroupServiceIds()
                    .stream()
                    .map(dto -> {
                        if (groupServiceRepository.IsGroupServiceExistByVendorAndGroup(exModel.getVendor().getUserId(), groupId, dto.getGroupServiceId()))
                            return groupServiceService.findById(dto.getGroupServiceId());
                        else {
                            throw new GenericException(GroupService.class, "is not valid with id",
                                    "groupServiceId", String.valueOf(dto.getGroupServiceId()));
                        }
                    })
                    .collect(Collectors.toSet()));
        }
        modelMapper.map(reqDTO, exModel);
        Contract resModel = contractRepository.save(exModel);
        //send notification
        sendNotification(resModel,
                Constant.Notification.UPDATE_CONTRACT,
                Constant.Notification.STATIC_UPDATE_CONTRACT_MESSAGE,
                resModel.getRenter().getFirebaseToken());

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

            /* Set contract id of booking when create corresponding contract */
            Booking exBooking = bookingService.findById(resModel.getBookingId());
            exBooking.setContractId(resModel.getContractId());
            bookingRepository.save(exBooking);
            // send notification
            sendNotification(resModel,
                    Constant.Notification.CONFIRM_CONTRACT,
                    Constant.Notification.STATIC_CONFIRM_CONTRACT_MESSAGE,
                    resModel.getVendor().getFirebaseToken());
            return resModel;
        }
        throw new GenericException(Contract.class, "qrCode not matched", "contractId", String.valueOf(exModel.getContractId()), "qrCode", exModel.getQrCode().toString());
    }


    @Override
    public List<Contract> findByRenterId(Long renterId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc == true ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return contractRepository.findByRenter_UserId(renterId, pageable);
    }

    @Override
    public List<Contract> findByVendorId(Long vendorId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc == true ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return contractRepository.findByVendor_UserId(vendorId, pageable);
    }

    /* Sub function for handling business */

    /**
     * @param model contract model
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
        /* Cancel all booking if the type out of room after create contract */
        int remainRoom = roomRepository.countByType_TypeIdAndIsAvailableIsTrue(resModel.getRoom().getType().getTypeId());
        if (remainRoom == 0) {
            Collection<Booking> incomingBookings = bookingRepository.findByType_TypeIdAndStatusIs(resModel.getRoom().getType().getTypeId(), Booking.STATUS.INCOMING);
            bookingService.cancelBookings(incomingBookings.stream().map(Booking::getBookingId).collect(Collectors.toList()));
        }

        return resModel;
    }

    private String uploadPDF(String contractHtml, String contractId) {
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
        contractInfo.put(Constant.Contract.RENTER_SCHOOL_ADDRESS, String.format("%s, %s", schoolDistrict, schoolProvince));

        contractInfo.put(Constant.Contract.DURATION, String.valueOf(model.getDuration()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(model.getStartTime());
        contractInfo.put(Constant.Contract.START_DAY, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        contractInfo.put(Constant.Contract.START_MONTH, String.valueOf(calendar.get(Calendar.MONTH) + 1));
        contractInfo.put(Constant.Contract.START_YEAR, String.valueOf(calendar.get(Calendar.YEAR)));

        Group group = model.getRoom().getType().getGroup();
        String buildingNo = group.getBuildingNo();
        String streetName = group.getAddress().getStreetName();
        String wardName = group.getAddress().getWardName();
        String districtName = group.getAddress().getDistrictName();
        String provinceName = group.getAddress().getProvinceName();
        contractInfo.put(Constant.Contract.ADDRESS, String.format("%s, %s, %s, %s, %s", buildingNo, streetName, wardName, districtName, provinceName));

//        String templateName = Utilities.getFileNameWithoutExtensionFromPath(contractTemplatePath);
        String contractHtml = Utilities.parseThymeleafTemplate(Constant.Contract.TEMPLATE_NAME, contractInfo);

        return contractHtml;
    }

    public void sendMailWithEmbed(String contractHtml, String receivedMail) {

        // Sender's email ID needs to be mentioned
        String from = "avenger.youthhostel@gmail.com";
        final String username = "avenger.youthhostel@gmail.com";
        final String password = "KieuTrongKhanh!$&1";

        Properties props = new Properties();
        props.put(Constant.Mail.MAIL_SMTP_AUTH, mailAuth);
        props.put(Constant.Mail.MAIL_SMTP_STARTTLS_ENABLE, mailStartTlsEnable);
        props.put(Constant.Mail.MAIL_SMTP_HOST, mailHost);
        props.put(Constant.Mail.MAIL_SMTP_PORT, mailPort);
        props.put(Constant.Mail.MAIL_SMTP_DEBUG, mailDebug);
        props.put(Constant.Mail.MAIL_SMTP_SOCKET_FACTORY_PORT, mailSocketFactoryPort);
        props.put(Constant.Mail.MAIL_SMTP_SOCKET_FACTORY_CLASS, mailSocketFactoryClass);
        props.put(Constant.Mail.MAIL_SMTP_SOCKET_FACTORY_FALLBACK, mailSocketFactoryFallback);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(adminGmailUsername, adminGmailPwd);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adminGmailUsername));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receivedMail));
            message.setSubject("Hợp đồng thuê nhà");
            message.setContent(contractHtml, "text/html; charset=UTF-8");
            // Send message
            Transport.send(message);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void sendNotification(Contract model, String action, String staticMsg, String destination) {

        String pattern = "dd/MM/yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String timestamp = simpleDateFormat.format(new Date());


        NotificationContent content = NotificationContent.builder()
                .id(String.valueOf(model.getContractId()))
                .action(action)
                .title(staticMsg + model.getRenter().getUsername())
                .body(timestamp)
                .icon(model.getRenter().getAvatar())
                .clickAction("")
                .build();

        ObjectMapper objMapper = new ObjectMapper();
        Map<String, String> data = objMapper.convertValue(content, Map.class);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .destination(destination)
                .data(data)
                .build();

        firebaseService.sendPnsToDevice(notificationRequest);
    }
}
