package org.avengers.capstone.hostelrenting.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import org.apache.commons.collections.CollectionUtils;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOConfirm;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.contract.ContractImageDTOCreate;
import org.avengers.capstone.hostelrenting.dto.contract.ContractImageDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationContent;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.exception.PreCreationException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.repository.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.util.BASE64DecodedMultipartFile;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Value("${azure.storage.contract-template}")
    private String templateUrl;

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
    private ContractImageService contractImageService;
    private ContractImageRepository contractImageRepository;
    private BookingService bookingService;
    private FileStorageServiceImp fileStorageService;
    private DealService dealService;
    private FirebaseService firebaseService;

    @Autowired
    public void setContractImageRepository(ContractImageRepository contractImageRepository) {
        this.contractImageRepository = contractImageRepository;
    }

    @Autowired
    public void setContractImageService(ContractImageService contractImageService) {
        this.contractImageService = contractImageService;
    }

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

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
        this.modelMapper.addMappings(skipUpdateFieldsMap);
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    PropertyMap<ContractDTOUpdate, Contract> skipUpdateFieldsMap = new PropertyMap<ContractDTOUpdate, Contract>() {
        protected void configure() {
            skip().setContractImages(null);
        }
    };

    @Override
    public void checkExist(Integer id) {
        Optional<Contract> model = contractRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
    }

    @Override
    public Contract findById(Integer id) {
        checkExist(id);

        return fillInContractObject(contractRepository.getOne(id));
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

        /* upload pdf and save the url */
        String contractHtml = generateContractHTML(resModel);
        String contractUrl = uploadPDF(contractHtml, String.valueOf(resModel.getContractId()));
        resModel.setContractUrl(contractUrl);
        resModel = contractRepository.save(resModel);

        /* send notification */
//        handleNotification(resModel);

        /* process business after create contract */
        processAfterCreate(resModel);

        return fillInContractObject(resModel);
    }

    @Override
    public Contract updateInactiveContract(Contract exModel, ContractDTOUpdate reqDTO) {
        // Only update INACTIVE contract
        if (!exModel.getStatus().equals(Contract.STATUS.INACTIVE)) {
            throw new GenericException(Contract.class, "only update with INACTIVE contract", "contractId", String.valueOf(exModel.getContractId()), "status", exModel.getStatus().toString());
        }

        // update room information
        Integer roomId = reqDTO.getRoomId();
        if (roomId != null) {
            if (!roomRepository.IsExistByVendorIdAndRoomId(exModel.getVendor().getUserId(), reqDTO.getRoomId()))
                throw new GenericException(Room.class, "is not valid with id",
                        "roomId", String.valueOf(reqDTO.getRoomId()));
            // make the old room available
            roomService.updateStatus(exModel.getRoom().getRoomId(), true);
            // lock the new room
            reqDTO.setRoom(roomService.findById(roomId));
            roomService.updateStatus(roomId, false);
        } else {
            roomId = exModel.getRoom().getRoomId();
        }
        int groupId = groupRepository.getGroupIdByRoomId(roomId);

        // update images
        if (reqDTO.getContractImages() != null) {
            Collection<ContractImage> deletedItems = exModel.getContractImages();
            deletedItems.removeAll(reqDTO.getContractImages()
                    .stream()
                    .map(contractImageDTOCreate -> {
                        if (contractImageRepository.findByResourceUrlAndContract_ContractId(contractImageDTOCreate.getResourceUrl(), exModel.getContractId()).isPresent())
                            return contractImageRepository.findByResourceUrlAndContract_ContractId(contractImageDTOCreate.getResourceUrl(), exModel.getContractId()).get();
                        return null;
                    }).collect(Collectors.toList()));

            for (ContractImage deletedItem : deletedItems) {
                deletedItem.setDeleted(true);
            }

            Collection<ContractImage> newItems = reqDTO.getContractImages()
                    .stream()
                    .map(contractImageDTOCreate -> {
                        Optional<ContractImage> exImg = contractImageRepository.findByResourceUrlAndContract_ContractId(contractImageDTOCreate.getResourceUrl(), exModel.getContractId());
                        if (exImg.isEmpty()) {
                            ContractImage img = modelMapper.map(contractImageDTOCreate, ContractImage.class);
                            img.setContract(exModel);
                            return img;
                        } else {
                            exImg.get().setDeleted(contractImageDTOCreate.isDeleted());
                            exImg.get().setReserved(contractImageDTOCreate.isReserved());
                            return exImg.get();
                        }
                    }).collect(Collectors.toList());
            exModel.getContractImages().addAll(newItems);
        }

        modelMapper.map(reqDTO, exModel);


        // handle with corresponding kind of contract
        if (exModel.isReserved()) {
            handleUpdateReversed(exModel);
        } else {
            handleUpdateNormalContract(exModel, reqDTO, groupId);
        }
        Contract resModel = contractRepository.save(exModel);


        //send notification
//        handleNotification(resModel);

        return fillInContractObject(resModel);
    }

    @Override
    public Contract confirm(Contract exModel, ContractDTOConfirm reqDTO) {
        /* Unable to update ACTIVATED contract */
        if ((exModel.getStatus() == Contract.STATUS.ACTIVATED && !reqDTO.getStatus().equals(Contract.STATUS.CANCELLED)) || exModel.getStatus().equals(Contract.STATUS.CANCELLED)) {
            throw new GenericException(Contract.class, "status cannot be updated", "contractId", String.valueOf(exModel.getContractId()), "status", exModel.getStatus().toString());
        }
        Contract.STATUS exStatus = exModel.getStatus();

        if (exModel.getQrCode().equals(reqDTO.getQrCode())) {
            modelMapper.map(reqDTO, exModel);

            /* set status based on isReserved */
            // change to CANCELLED from all status
            if (reqDTO.getStatus().equals(Contract.STATUS.CANCELLED)) {
                exModel.setStatus(Contract.STATUS.CANCELLED);
                // update room status when cancelled
                roomService.updateStatus(exModel.getRoom().getRoomId(), true);
            }// change to RESERVED only from ACCEPTED
            else if (reqDTO.getStatus().equals(Contract.STATUS.RESERVED) && exStatus.equals(Contract.STATUS.ACCEPTED)) {
                exModel.setStatus(Contract.STATUS.RESERVED);
            }// change to ACTIVATED only from INACTIVE
            else if (reqDTO.getStatus().equals(Contract.STATUS.ACTIVATED) && (exStatus.equals(Contract.STATUS.INACTIVE) || exStatus.equals(Contract.STATUS.RESERVED))) {
                exModel.setStatus(Contract.STATUS.ACTIVATED);
                /* send mail for both renter and vendor when active contract */
                String contractHtml = generateContractHTML(exModel);
                sendMailWithEmbed(contractHtml, exModel.getRenter().getEmail());
                sendMailWithEmbed(contractHtml, exModel.getVendor().getEmail());

            }// change to ACCEPTED only from INACTIVE
            else if (reqDTO.getStatus().equals(Contract.STATUS.ACCEPTED) && exStatus.equals(Contract.STATUS.INACTIVE)) {
                exModel.setStatus((Contract.STATUS.ACCEPTED));
            } else {
                throw new GenericException(Contract.class, "Invalid status for updating", "from", String.valueOf(exStatus), "to", String.valueOf(reqDTO.getStatus()));
            }

            // change qrCode after confirm success
            //TODO: change after getting qrCode 60s
            exModel.setQrCode(UUID.randomUUID());

            Contract resModel = contractRepository.save(exModel);

            /* Set contract id of booking when create corresponding contract */
            if (resModel.getBookingId() != null) {
                Booking exBooking = bookingService.findById(resModel.getBookingId());
                exBooking.setContractId(resModel.getContractId());
                bookingRepository.save(exBooking);
            }

            /* send notification */
//            if (!resModel.getStatus().equals(Contract.STATUS.CANCELLED))
//                handleNotification(resModel);
            return fillInContractObject(resModel);
        }
        throw new GenericException(Contract.class, "qrCode not matched", "contractId", String.valueOf(exModel.getContractId()), "qrCode", exModel.getQrCode().toString());
    }

    @Override
    public List<Contract> findByRenterId(Long renterId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return contractRepository.findByRenter_UserId(renterId, pageable).stream().map(this::fillInContractObject).collect(Collectors.toList());
    }

    @Override
    public List<Contract> findByVendorId(Long vendorId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return contractRepository.findByVendor_UserId(vendorId, pageable).stream().map(this::fillInContractObject).collect(Collectors.toList());
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

        /* Check whether given booking id belong to any contract or not? */
        if (bookingRepository.findByBookingIdAndContractIdIsNotNull(model.getBookingId()).isPresent()) {
            errMsg = String.format("Only create 1 contract from 1 booking. Booking {id=%s} has been linked with another contract", model.getBookingId());
            isViolated = true;
        }

        /* Check whether isReserved and downPayment valid or not */
        if (model.isReserved()) {
            if (model.getDownPayment() == null) {
                throw new GenericException(Contract.class, "down payment is required with reversed", "downPayment", String.valueOf(model.getDownPayment()));
            }
        }

        if (isViolated)
            throw new GenericException(Contract.class, errMsg);
    }

    private void processAfterCreate(Contract resModel) {
        /* Cancel all booking if the type out of room after create contract */
        int remainRoom = roomRepository.countByType_TypeIdAndIsAvailableIsTrue(resModel.getRoom().getType().getTypeId());
        if (remainRoom == 0) {
            Collection<Booking> incomingBookings = bookingRepository.findByType_TypeIdAndStatusIs(resModel.getRoom().getType().getTypeId(), Booking.STATUS.INCOMING);
            bookingService.cancelBookings(incomingBookings.stream().map(Booking::getBookingId).collect(Collectors.toList()));
        }
        // set contract id for booking
        if (resModel.getBookingId() != null) {
            Booking exBooking = bookingService.findById(resModel.getBookingId());
            exBooking.setContractId(resModel.getContractId());
            bookingRepository.save(exBooking);
        }
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

        String templateContent = null;
        try {
            templateContent = new Scanner(new URL(templateUrl).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GenericException(Contract.class, "fail to confirm", "contractId", String.valueOf(model.getContractId()));
        }
        String contractHtml = Utilities.parseThymeleafTemplate(templateContent, contractInfo);

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

//    private void sendNotification(Contract model, String action, String staticMsg, String destination) {
//
//        String pattern = "dd/MM/yyyy hh:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String timestamp = simpleDateFormat.format(new Date());
//
//
//        NotificationContent content = NotificationContent.builder()
//                .id(String.valueOf(model.getContractId()))
//                .action(action)
//                .title(staticMsg)
//                .body(timestamp)
//                .icon(model.getRenter().getAvatar())
//                .clickAction("")
//                .build();
//
//        ObjectMapper objMapper = new ObjectMapper();
//        Map<String, String> data = objMapper.convertValue(content, Map.class);
//
//        NotificationRequest notificationRequest = NotificationRequest.builder()
//                .destination(destination)
//                .data(data)
//                .build();
//
//        firebaseService.sendPnsToDevice(notificationRequest);
//    }

    private Contract fillInContractObject(Contract model) {
        model.setType(model.getRoom().getType());
        model.setGroup(model.getRoom().getType().getGroup());
        if (model.getDealId() != null)
            model.setDeal(dealService.findById(model.getDealId()));
        if (model.getBookingId() != null)
            model.setBooking(bookingService.findById(model.getBookingId()));
        return model;
    }

    private void handleUpdateReversed(Contract model) {
        model.setStartTime(model.getCreatedAt());
    }

    private void handleUpdateNormalContract(Contract exModel, ContractDTOUpdate dto, int groupId) {
        //check whether groupService is valid or not
        if (dto.getGroupServiceIds() != null && !dto.getGroupServiceIds().isEmpty()) {
            dto.setGroupServices(dto.getGroupServiceIds()
                    .stream()
                    .map(idDto -> {
                        if (groupServiceRepository.IsGroupServiceExistByVendorAndGroup(exModel.getVendor().getUserId(), groupId, idDto.getGroupServiceId()))
                            return groupServiceService.findById(idDto.getGroupServiceId());
                        else {
                            throw new GenericException(GroupService.class, "is not valid with id",
                                    "groupServiceId", String.valueOf(idDto.getGroupServiceId()));
                        }
                    })
                    .collect(Collectors.toSet()));
        }
    }

    private void basicValidContract(Contract model, Contract.STATUS status) {
        if ((model.getStatus() == Contract.STATUS.ACTIVATED && !status.equals(Contract.STATUS.CANCELLED)) || model.getStatus().equals(Contract.STATUS.CANCELLED)) {
            throw new GenericException(Contract.class, "cannot be updated", "contractId", String.valueOf(model.getContractId()), "status", model.getStatus().toString());
        }
    }

//    private void handleNotification(Contract model) {
//        String action = "", message = "";
//
//        ArrayList<String> destinations = new ArrayList<>();
//        switch (model.getStatus()) {
//            case INACTIVE:
//                if (model.isReserved() && model.getUpdatedAt() == null) {
//                    action = Constant.Notification.NEW_RESERVED;
//                    message = Constant.Notification.STATIC_NEW_RESERVED_MESSAGE + model.getVendor().getUsername();
//                    destinations.add(model.getRenter().getFirebaseToken());
//                } else if (!model.isReserved() && model.getUpdatedAt() == null) {
//                    action = Constant.Notification.NEW_CONTRACT;
//                    message = Constant.Notification.STATIC_NEW_CONTRACT_MESSAGE + model.getVendor().getUsername();
//                    destinations.add(model.getRenter().getFirebaseToken());
//                } else if (model.isReserved()) {
//                    action = Constant.Notification.UPDATE_CONTRACT;
//                    message = Constant.Notification.STATIC_UPDATE_CONTRACT_MESSAGE + model.getVendor().getUsername();
//                    destinations.add(model.getRenter().getFirebaseToken());
//                } else if (!model.isReserved()) {
//                    action = Constant.Notification.UPDATE_RESERVED;
//                    message = Constant.Notification.STATIC_UPDATE_RESERVED_MESSAGE + model.getVendor().getUsername();
//                    destinations.add(model.getRenter().getFirebaseToken());
//                }
//                break;
//            case RESERVED:
//            case ACTIVATED:
//                if (model.isReserved()) {
//                    action = Constant.Notification.CONFIRMAC;
//                    message = Constant.Notification.STATIC_CONFIRM_RESERVED_MESSAGE + model.getRenter().getUsername();
//                    destinations.add(model.getVendor().getFirebaseToken());
//                } else if (!model.isReserved()) {
//                    action = Constant.Notification.CONFIRM_CONTRACT;
//                    message = Constant.Notification.CONFIRM_CONTRACT + model.getRenter().getUsername();
//                    destinations.add(model.getVendor().getFirebaseToken());
//                }
//            case EXPIRED:
//            case CANCELLED:
//                action = Constant.Notification.CANCELLED_CONTRACT;
//                message = Constant.Notification.STATIC_CANCELLED_CONTRACT_MESSAGE;
//                destinations.add(model.getVendor().getFirebaseToken());
//                destinations.add(model.getRenter().getFirebaseToken());
//                break;
//        }
//
//        for (String token : destinations) {
//            sendNotification(model, action, message, token);
//        }
//    }
}
