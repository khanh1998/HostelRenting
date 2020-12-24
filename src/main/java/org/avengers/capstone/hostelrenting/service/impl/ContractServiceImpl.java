package org.avengers.capstone.hostelrenting.service.impl;


import com.lowagie.text.DocumentException;
import org.apache.commons.collections.CollectionUtils;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOConfirm;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.exception.PreCreationException;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.model.*;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
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

    @Value("${contract.reserved.expiring.day}")
    private String reservedContractExpiredDayRange;

    private Utilities utilities;
    private ContractRepository contractRepository;
    private GroupServiceRepository groupServiceRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private GroupRepository groupRepository;
    private ModelMapper modelMapper;
    private RoomService roomService;
    private GroupServiceService groupServiceService;
    private ContractImageRepository contractImageRepository;
    private BookingService bookingService;
    private FileStorageServiceImp fileStorageService;
    private DealService dealService;

    @Autowired
    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    @Autowired
    public void setContractImageRepository(ContractImageRepository contractImageRepository) {
        this.contractImageRepository = contractImageRepository;
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
        // cannot mapping image -> handle manually
        this.modelMapper.addMappings(skipUpdateFieldsMap);
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    PropertyMap<ContractDTOUpdate, Contract> skipUpdateFieldsMap = new PropertyMap<>() {
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
        Optional<Contract> tempContract = contractRepository.findByVendor_UserIdAndRenter_UserIdAndRoom_RoomIdAndStatus(
                reqModel.getVendor().getUserId(),
                reqModel.getRenter().getUserId(),
                reqModel.getRoom().getRoomId(),
                Contract.STATUS.INACTIVE);
        if (tempContract.isPresent())
            throw new GenericException(Contract.class, "Contract has already with ",
                    "contractId", String.valueOf(tempContract.get().getContractId()),
                    "renterId", String.valueOf(tempContract.get().getRenter().getUserId()),
                    "vendorId", String.valueOf(tempContract.get().getVendor().getUserId()),
                    "status", String.valueOf(tempContract.get().getStatus()));

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
        String contractHtml = generateContractHTML(resModel, false);
        String contractUrl = uploadPDF(contractHtml, String.valueOf(resModel.getContractId()));
        resModel.setContractUrl(contractUrl);
        resModel = contractRepository.save(resModel);

        /* process business after create contract */
        processAfterCreate(resModel);

        return fillInContractObject(resModel);
    }

    @Override
    public Contract updateInactiveContract(Contract exModel, ContractDTOUpdate reqDTO) {
        //TODO: only update image with ACCEPTED. Update full info with INACTIVE
        if (!includeStatuses(exModel, Contract.STATUS.INACTIVE, Contract.STATUS.ACCEPTED, Contract.STATUS.RESERVED, Contract.STATUS.ACTIVATED)) {
            throw new GenericException(Contract.class, "cannot update with {status=" + exModel.getStatus() + "}. Only update with",
                    "status_1", String.valueOf(Contract.STATUS.INACTIVE),
                    "status_2", String.valueOf(Contract.STATUS.ACCEPTED),
                    "status_3", String.valueOf(Contract.STATUS.RESERVED));
        }

        // update all basic info with INACTIVE
        if (includeStatuses(exModel, Contract.STATUS.INACTIVE)) {
            updateContractBasicInfo(exModel, reqDTO);
            modelMapper.map(reqDTO, exModel);
        }

        //update payment image info and isPaid - ACCEPTED, INACTIVE, RESERVED
        if (includeStatuses(exModel, Contract.STATUS.ACCEPTED, Contract.STATUS.INACTIVE, Contract.STATUS.RESERVED)) {
            updateContractImages(exModel, reqDTO);
            // if isPaid -> update isPaid
            if (reqDTO.isPaid()) {
                exModel.setPaid(reqDTO.isPaid());
            }
        }

        // update resign - ACTIVATED
        if (includeStatuses(exModel, Contract.STATUS.ACTIVATED)) {
            exModel.setResign(reqDTO.getResign());
        }

        // common update for all cases
//        exModel.setUpdatedAt(reqDTO.getUpdatedAt());
        Contract resModel = contractRepository.save(exModel);

        return fillInContractObject(resModel);
    }

    @Override
    public Contract confirm(Contract exModel, ContractDTOConfirm reqDTO) {
        if ((exModel.getStatus() == Contract.STATUS.ACTIVATED && !reqDTO.getStatus().equals(Contract.STATUS.CANCELLED))
                || includeStatuses(exModel, Contract.STATUS.CANCELLED, Contract.STATUS.EXPIRED)) {
            throw new GenericException(Contract.class, "status cannot be updated", "contractId", String.valueOf(exModel.getContractId()), "status", exModel.getStatus().toString());
        }
        Contract.STATUS exStatus = exModel.getStatus();

        if (exModel.getQrCode().equals(reqDTO.getQrCode())) {
            /* set status based on isReserved */
            // change to CANCELLED from all status
            if (reqDTO.getStatus().equals(Contract.STATUS.CANCELLED)) {
                exModel.setStatus(Contract.STATUS.CANCELLED);
                // update room status when cancelled
                roomService.updateStatus(exModel.getRoom().getRoomId(), true);
            }
            // change to RESERVED only from ACCEPTED
            else if (reqDTO.getStatus().equals(Contract.STATUS.RESERVED) && includeStatuses(exModel, Contract.STATUS.ACCEPTED)) {
                exModel.setStatus(Contract.STATUS.RESERVED);
                // isPaid false for reuse when do the rest payment
                exModel.setPaid(false);
                // start time begin when vendor accept with payment information
                exModel.setStartTime(utilities.getCurrentTime());
                // change last_pay at the time vendor confirm
                exModel.setLastPayAt(utilities.getCurrentTime());
            }
            // change to ACTIVATED only from INACTIVE
            else if (reqDTO.getStatus().equals(Contract.STATUS.ACTIVATED) && (includeStatuses(exModel, Contract.STATUS.ACCEPTED, Contract.STATUS.RESERVED))) {
                exModel.setStatus(Contract.STATUS.ACTIVATED);
                /* send mail for both renter and vendor when active contract */
                String contractHtml = generateContractHTML(exModel, true);
                utilities.sendMailWithEmbed(Constant.Contract.SUBJECT_CREATE_NEW, contractHtml, exModel.getRenter().getEmail());
                utilities.sendMailWithEmbed(Constant.Contract.SUBJECT_CREATE_NEW, contractHtml, exModel.getVendor().getEmail());
                // change last_pay at the time vendor confirm
                exModel.setLastPayAt(utilities.getCurrentTime());
            }
            // change to ACCEPTED only from INACTIVE
            else if (reqDTO.getStatus().equals(Contract.STATUS.ACCEPTED) && includeStatuses(exModel, Contract.STATUS.INACTIVE)) {
                exModel.setStatus((Contract.STATUS.ACCEPTED));
                exModel.setLastPayAt(utilities.getCurrentTime());
                /* upload pdf and save the url */
                String contractHtml = generateContractHTML(exModel, false);
                String contractUrl = uploadPDF(contractHtml, String.valueOf(exModel.getContractId()));
                exModel.setContractUrl(contractUrl);
                exModel = contractRepository.save(exModel);
            } else {
                throw new GenericException(Contract.class, "Invalid status for updating", "from", String.valueOf(exModel.getStatus()), "to", String.valueOf(reqDTO.getStatus()));
            }

            modelMapper.map(reqDTO, exModel);
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

            return fillInContractObject(resModel);
        }
        throw new GenericException(Contract.class, "qrCode not matched", "contractId", String.valueOf(exModel.getContractId()), "qrCode", exModel.getQrCode().toString());
    }

    @Override
    public List<Contract> findByRenterId(UUID renterId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - Constant.ONE, size, sort);
        return contractRepository.findByRenter_UserId(renterId, pageable).stream().map(this::fillInContractObject).collect(Collectors.toList());
    }

    @Override
    public List<Contract> findByVendorId(UUID vendorId, int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - Constant.ONE, size, sort);
        return contractRepository.findByVendor_UserId(vendorId, pageable).stream().map(this::fillInContractObject).collect(Collectors.toList());
    }


    /* Sub function for handling business */

    private void updateContractImages(Contract exModel, ContractDTOUpdate reqDTO) {
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
                            exImg.get().setType(contractImageDTOCreate.getType());
                            return exImg.get();
                        }
                    }).collect(Collectors.toList());
            exModel.getContractImages().addAll(newItems);
        }
    }

    private void updateContractBasicInfo(Contract exModel, ContractDTOUpdate reqDTO) {
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

        //check whether groupService is valid or not
        if (reqDTO.getGroupServiceIds() != null && !reqDTO.getGroupServiceIds().isEmpty()) {
            reqDTO.setGroupServices(reqDTO.getGroupServiceIds()
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

    /**
     * @param reqModel contract model
     */
    private void validatePreCreate(Contract reqModel, Collection<Integer> reqServiceIds, Collection<GroupService> availableServices) throws PreCreationException {
        int groupId = reqModel.getRoom().getType().getGroup().getGroupId();
        int roomId = reqModel.getRoom().getRoomId();
        boolean isViolated = false;
        String errMsg = null;

        /* Violate if room id is not available for create new contract (differ renter)*/
        /* Violate if the new contract has startTime invalid (newContractStartTime < oldContractEndTime) */
        checkRoom(reqModel);

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
        if (bookingRepository.findByBookingIdAndContractIdIsNotNull(reqModel.getBookingId()).isPresent()) {
            errMsg = String.format("Only create 1 contract from 1 booking. Booking {id=%s} has been linked with another contract", reqModel.getBookingId());
            isViolated = true;
        }

        /* Check whether isReserved and downPayment valid or not */
        if (reqModel.isReserved()) {
            if (reqModel.getDownPayment() == null) {
                throw new GenericException(Contract.class, "down payment is required with reversed", "downPayment", String.valueOf(reqModel.getDownPayment()));
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

    public String generateContractHTML(Contract model, boolean isEmail) {
        Map<String, Object> contractInfo = new HashMap<>();
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
        // get current time (miliseconds)
        calendar.getTimeInMillis();
        contractInfo.put(Constant.Contract.CURRENT_DAY, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        contractInfo.put(Constant.Contract.CURRENT_MONTH, String.valueOf(calendar.get(Calendar.MONTH) + Constant.ONE));
        contractInfo.put(Constant.Contract.CURRENT_YEAR, String.valueOf(calendar.get(Calendar.YEAR)));

        // get start time in contract (miliseconds)
        calendar.setTimeInMillis(model.getStartTime());
        contractInfo.put(Constant.Contract.START_DAY, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        contractInfo.put(Constant.Contract.START_MONTH, String.valueOf(calendar.get(Calendar.MONTH) + Constant.ONE));
        contractInfo.put(Constant.Contract.START_YEAR, String.valueOf(calendar.get(Calendar.YEAR)));

        // end time of contract
        calendar.add(Calendar.MONTH, model.getDuration());
        contractInfo.put(Constant.Contract.END_DAY, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        contractInfo.put(Constant.Contract.END_MONTH, String.valueOf(calendar.get(Calendar.MONTH) + Constant.ONE));
        contractInfo.put(Constant.Contract.END_YEAR, String.valueOf(calendar.get(Calendar.YEAR)));

        // room number
        contractInfo.put(Constant.Contract.ROOM_NUMBER, model.getRoom().getRoomName());

        // superficiality
        contractInfo.put(Constant.Contract.GROUP_SUPERFICIALITY, String.valueOf(model.getRoom().getType().getSuperficiality()));

        // Number format
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(Constant.ZERO);

        // type deposit + renting price
        if (model.getDealId() != null) {
            contractInfo.put(Constant.Contract.TYPE_DEPOSIT, nf.format(model.getRoom().getType().getDeposit() * model.getDeal().getOfferedPrice() * Constant.ONE_MILION));
            contractInfo.put(Constant.Contract.RENTING_PRICE, nf.format(model.getDeal().getOfferedPrice() * Constant.ONE_MILION));
        } else {
            contractInfo.put(Constant.Contract.TYPE_DEPOSIT, nf.format(model.getRoom().getType().getDeposit() * model.getRoom().getType().getPrice() * Constant.ONE_MILION));
            contractInfo.put(Constant.Contract.RENTING_PRICE, nf.format(model.getRoom().getType().getPrice() * Constant.ONE_MILION));
        }

        // renter confirm text when status is ACCEPTED, RESERVED, ACTIVATED
        if (includeStatuses(model, Contract.STATUS.ACCEPTED, Contract.STATUS.RESERVED, Contract.STATUS.ACTIVATED)) {
            contractInfo.put(Constant.Contract.RENTER_CONFIRM_TEXT, Constant.Contract.RENTER_CONFIRM_TEXT_CONTENT);
        }


        // appendix contract
        contractInfo.put(Constant.Contract.APPENDIX_CONTRACT, model.getAppendixContract());

        // appendix contract
        contractInfo.put(Constant.Contract.PAYMENT_DAY_IN_MONTH, String.valueOf(model.getPaymentDayInMonth()));

        // down payment
        if (model.getDownPayment() == null) {
            contractInfo.put(Constant.Contract.DOWN_PAYMENT, nf.format(Constant.ZERO));
        } else {
            contractInfo.put(Constant.Contract.DOWN_PAYMENT, nf.format(model.getDownPayment() * Constant.ONE_MILION));
        }

        // contractUrl
        if (model.getContractUrl()!= null
                && includeStatuses(model, Contract.STATUS.ACCEPTED, Contract.STATUS.RESERVED, Contract.STATUS.ACTIVATED)){
            contractInfo.put(Constant.Contract.CONTRACT_PDF_URL, Constant.Contract.CONTRACT_PDF_URL_CONTENT + model.getContractUrl());
        }else{
            contractInfo.put(Constant.Contract.CONTRACT_PDF_URL, Constant.EMPTY_STRING);
        }

        // reserved Contract Expired Day Range
        contractInfo.put(Constant.Contract.RESERVED_CONTRACT_EXPIRED_DAY_RANGE, reservedContractExpiredDayRange);

        model.getGroupServices()
                .forEach(groupService -> {
                    groupService.setDisplayPrice(nf.format((long) Math.round(groupService.getPrice()) * Constant.ONE_THOUSAND));
                });

        contractInfo.put(Constant.Contract.SERVICES, model.getGroupServices());
        contractInfo.put(Constant.Contract.REGULATIONS, model.getRoom().getType().getGroup().getGroupRegulations());
        contractInfo.put(Constant.Contract.FACILITIES, model.getRoom().getType().getTypeFacilities());


        // Get full address
        Group group = model.getRoom().getType().getGroup();
        String buildingNo = group.getBuildingNo();
        String streetName = group.getAddress().getStreetName();
        String wardName = group.getAddress().getWardName();
        String districtName = group.getAddress().getDistrictName();
        String provinceName = group.getAddress().getProvinceName();
        contractInfo.put(Constant.Contract.ADDRESS, String.format("%s, %s, %s, %s, %s", buildingNo, streetName, wardName, districtName, provinceName));

        String templateContent;
        try {
            templateContent = new Scanner(new URL(templateUrl).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GenericException(Contract.class, "fail to confirm", "contractId", String.valueOf(model.getContractId()));
        }

        return Utilities.parseThymeleafTemplate(templateContent, contractInfo);
    }


    private Contract fillInContractObject(Contract model) {
        model.setType(model.getRoom().getType());
        model.setGroup(model.getRoom().getType().getGroup());
        Integer dealId = model.getDealId();
        Integer bookingId = model.getBookingId();
        if (dealId != null)
            model.setDeal(dealService.findById(dealId));
        if (bookingId != null) {
            model.setBooking(bookingService.findById(bookingId));
            if (bookingService.findById(model.getBookingId()).getDealId() != null)
                model.setDeal(dealService.findById(bookingService.findById(bookingId).getDealId()));
        }
        return model;
    }

    private boolean includeStatuses(Contract model, Contract.STATUS... statuses) {
        for (Contract.STATUS status : statuses) {
            if (model.getStatus().equals(status))
                return true;
        }
        return false;
    }

    private void checkRoom(Contract reqModel) {
        int roomId = reqModel.getRoom().getRoomId();
        Long startTime = reqModel.getStartTime();
        UUID reqRenterId = reqModel.getRenter().getUserId();
        UUID reqVendorId = reqModel.getVendor().getUserId();

        //find the contract with renterId, roomID and ACTIVATED status
        Optional<Contract> exContract = contractRepository.findByVendor_UserIdAndRenter_UserIdAndRoom_RoomIdAndStatus(reqVendorId, reqRenterId, roomId, Contract.STATUS.ACTIVATED);
        if (exContract.isPresent()) {
            // Calculate the expired time of the ex contract
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(exContract.get().getStartTime());
            cal.add(Calendar.MONTH, exContract.get().getDuration());
            Long endTime = cal.getTimeInMillis();
            // case: startTime before endTime
            if (reqModel.getStartTime() < endTime) {
                throw new GenericException(Contract.class, "has invalid startTime, the old contract has not expired yet",
                        "Old contract end time", String.valueOf(endTime),
                        "Your start time", String.valueOf(reqModel.getStartTime()));
            }
        } else {
            if (!roomService.checkAvailableById(roomId))
                throw new GenericException(Room.class, "is not available", "roomId", String.valueOf(roomId));
        }
    }
}
