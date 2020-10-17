package org.avengers.capstone.hostelrenting.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.PreCreationException;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.repository.GroupServiceRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    private ContractRepository contractRepository;
    private GroupServiceRepository groupServiceRepository;
    private ModelMapper modelMapper;
    private RenterService renterService;
    private VendorService vendorService;
    private RoomService roomService;
    private GroupServiceService groupServiceService;
    private DealService dealService;
    private BookingService bookingService;

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
    public void checkActive(Integer id) {
        Optional<Contract> model = contractRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
    }

    @Override
    public Contract findById(Integer id) {
        checkActive(id);

        return contractRepository.getOne(id);
    }

    @Override
    public Contract create(Contract reqModel) throws PreCreationException {
        int groupId = reqModel.getRoom().getType().getGroup().getGroupId();
        Collection<Integer> reqServiceIds = reqModel.getGroupServices().stream().map(GroupService::getGroupServiceId).collect(Collectors.toList());
        Collection<GroupService> availableServices = groupServiceRepository.findByGroup_GroupIdAndIsActiveIsTrue(groupId);

        validatePreCreate(reqModel, reqServiceIds, availableServices);

        Collection<GroupService> validServices = reqServiceIds.stream().map(id -> groupServiceService.findById(id)).collect(Collectors.toList());
        /* Set groupServices for contract model */
        reqModel.setGroupServices(validServices);
        /* Set room for contract model */
        reqModel.setRoom(roomService.updateStatus(reqModel.getRoom().getRoomId(), false));

        return contractRepository.save(reqModel);
    }

    @Override
    public Contract update(Contract reqModel) {
        return null;
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
}
