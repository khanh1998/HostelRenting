package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private DealService dealService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    private ModelMapper modelMapper;
//    private RenterService renterService;
//    private VendorService vendorService;
//    private HostelRoomService roomService;
//    private DealService dealService;
//    private BookingService bookingService;
//
//    @Autowired
//    public void setDealService(DealService dealService) {
//        this.dealService = dealService;
//    }
//
//    @Autowired
//    public void setBookingService(BookingService bookingService) {
//        this.bookingService = bookingService;
//    }
//
//    @Autowired
//    public void setRoomService(HostelRoomService roomService) {
//        this.roomService = roomService;
//    }
//
//    @Autowired
//    public void setVendorService(VendorService vendorService) {
//        this.vendorService = vendorService;
//    }
//
//    @Autowired
//    public void setRenterService(RenterService renterService) {
//        this.renterService = renterService;
//    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
//
//    @Autowired
//    public void setContractRepository(ContractRepository contractRepository) {
//        this.contractRepository = contractRepository;
//    }


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
    public Contract create(ContractDTOShort reqDTO) {
//        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
//        Renter exRenter = renterService.findById(reqDTO.getVendorId());
//        Room exRoom = roomService.findById(reqDTO.getRoomId());
        Vendor exVendor = vendorRepository.findById(reqDTO.getVendorId()).get();
        Renter exRenter = renterRepository.findById(reqDTO.getVendorId()).get();
        Room exRoom = roomRepository.findById(reqDTO.getRoomId()).get();

        Contract reqModel = modelMapper.map(reqDTO, Contract.class);

        // Update deal status if exist (contract created means deal is done)
        // Update booking status if exist (contract created means booking is done)
        Integer dealId = reqDTO.getDealId();
        Integer bookingId = reqDTO.getBookingId();
        if (dealId != null){
//            dealService.checkActive(dealId);
//            dealService.changeStatus(dealId, Deal.STATUS.DONE);
            if (dealRepository.existsById(dealId)){
                dealService.changeStatus(dealId, Deal.STATUS.DONE);
            }else{
                throw new EntityNotFoundException(Deal.class, "id", dealId.toString());
            }
        }
        if (bookingId != null){
//            bookingService.checkActive(bookingId);
//            bookingService.changeStatus(bookingId, Booking.STATUS.DONE);
            if (bookingRepository.existsById(bookingId)){
                bookingService.changeStatus(bookingId, Booking.STATUS.DONE);
            }else{
                throw new EntityNotFoundException(Booking.class, "id", bookingId.toString());
            }
        }

        reqModel.setStatus(Contract.STATUS.WORKING);
        reqModel.setVendor(exVendor);
        reqModel.setRenter(exRenter);
        reqModel.setRoom(exRoom);
        reqModel.setCreatedAt(System.currentTimeMillis());

        return contractRepository.save(reqModel);
    }

    @Override
    public Contract update(ContractDTOShort reqDTO) {
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
//        renterService.checkExist(renterId);
//
//        return renterService.findById(renterId).getContracts();
        if (renterRepository.existsById(renterId)){
            return renterRepository.findById(renterId).get().getContracts();
        }else{
            throw new EntityNotFoundException(Renter.class, "id", renterId.toString());
        }
    }

    /**
     * Get list of contracts by given vendor id
     *
     * @param vendorId given vendor id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByVendorId(Long vendorId) {
//        vendorService.checkExist(vendorId);
//
//        return vendorService.findById(vendorId).getContracts();
        if (vendorRepository.existsById(vendorId)){
            return vendorRepository.findById(vendorId).get().getContracts();
        }else{
            throw new EntityNotFoundException(Vendor.class, "id", vendorId.toString());
        }
    }

    private void setUpdatedTime(Contract exModel){
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
