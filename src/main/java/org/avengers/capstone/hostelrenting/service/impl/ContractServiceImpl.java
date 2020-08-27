package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private ModelMapper modelMapper;
    private RenterService renterService;
    private VendorService vendorService;
    private HostelRoomService roomService;
    private DealService dealService;
    private BookingService bookingService;

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setRoomService(HostelRoomService roomService) {
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
        else if (model.get().isDeleted())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
    }

    @Override
    public Contract findById(Integer id) {
        checkActive(id);

        return contractRepository.getOne(id);
    }

    @Override
    public Contract create(ContractDTOShort reqDTO) {
        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
        Renter exRenter = renterService.findById(reqDTO.getVendorId());
        HostelRoom exRoom = roomService.findById(reqDTO.getRoomId());

        Contract reqModel = modelMapper.map(reqDTO, Contract.class);

        // Update deal status if exist (contract created means deal is done)
        // Update booking status if exist (contract created means booking is done)
        Integer dealId = reqDTO.getDealId();
        Integer bookingId = reqDTO.getBookingId();
        if (dealId != null){
            dealService.checkActive(dealId);
            dealService.changeStatus(dealId, Deal.STATUS.DONE);
        }
        if (bookingId != null){
            bookingService.checkActive(bookingId);
            bookingService.changeStatus(bookingId, Booking.STATUS.DONE);
        }

        reqModel.setVendor(exVendor);
        reqModel.setRenter(exRenter);
        reqModel.setHostelRoom(exRoom);
        reqModel.setCreatedAt(System.currentTimeMillis());
        if (reqModel.getDurationUnit()== null)
            reqModel.setDurationUnit(Contract.DURATION_UNIT.MONTH);

        return contractRepository.save(reqModel);
    }

    @Override
    public Contract update(ContractDTOShort reqDTO) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        checkActive(id);
        Contract exModel = contractRepository.getOne(id);
        if (exModel.isDeleted())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
        exModel.setDeleted(true);
        setUpdatedTime(exModel);
        contractRepository.save(exModel);
    }

    /**
     * Get list of contracts by given renter id
     *
     * @param renterId given renter id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByRenterId(Integer renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getContracts()
                .stream()
                .filter(contract -> !contract.isDeleted())
                .collect(Collectors.toList());
    }

    /**
     * Get list of contracts by given vendor id
     *
     * @param vendorId given vendor id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByVendorId(Integer vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getContracts()
                .stream()
                .filter(contract -> !contract.isDeleted())
                .collect(Collectors.toList());
    }

    private void setUpdatedTime(Contract exModel){
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
