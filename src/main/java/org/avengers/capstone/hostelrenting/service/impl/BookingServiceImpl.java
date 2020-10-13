package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {

//    @Autowired
    private BookingRepository bookingRepository;
//    @Autowired
//    private VendorService vendorService;
//    @Autowired
//    private RenterService renterService;
    @Autowired
    private DealService dealService;
//    @Autowired
//    private HostelTypeService hostelTypeService;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private TypeRepository hostelTypeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    @Autowired
//    public void setVendorService(VendorService vendorService) {
//        this.vendorService = vendorService;
//    }
//
//    @Autowired
//    public void setRenterService(RenterService renterService) {
//        this.renterService = renterService;
//    }
//
//    @Autowired
//    public void setHostelTypeService(HostelTypeService hostelTypeService) {
//        this.hostelTypeService = hostelTypeService;
//    }
//
//    @Autowired
//    public void setDealService(DealService dealService) {
//        this.dealService = dealService;
//    }
//
    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
//
//    @Override
//    public void checkActive(Integer id) {
//        Optional<Booking> model = bookingRepository.findById(id);
//        if (model.isEmpty())
//            throw new EntityNotFoundException(Booking.class, "id", id.toString());
//    }
//
//    @Override
//    public Booking findById(Integer id) {
//        checkActive(id);
//        return bookingRepository.getOne(id);
//
//    }

    @Override
    public Booking findById(Integer id) {
//        if (bookingRepository.existsById(id))
//            return bookingRepository.findById(id).get();
//        else
//            throw new EntityNotFoundException(Booking.class, "id", id.toString());
        try{
            return bookingRepository.findById(id).get();
        }catch (Exception e){
//            throw new EntityNotFoundException(Booking.class, "id", id.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Booking create(BookingDTOShort reqDTO) {

        Vendor exVendor = vendorRepository.findById(reqDTO.getVendorId()).get();
        Renter exRenter = renterRepository.findById(reqDTO.getRenterId()).get();
        Type exType = hostelTypeRepository.findById(reqDTO.getTypeId()).get();

        Booking reqModel = modelMapper.map(reqDTO, Booking.class);

        // Update deal status if exist (booking created means deal is done)
        Integer dealId = reqDTO.getDealId();
        if (dealId != null) {
//            dealService.checkActive(dealId);
//            dealService.changeStatus(dealId, Deal.STATUS.DONE);
            if (dealRepository.existsById(dealId)){
                dealService.changeStatus(dealId, Deal.STATUS.DONE);
            }else{
                throw new EntityNotFoundException(Deal.class, "id", dealId.toString());
            }
        }

        reqModel.setType(exType);
        reqModel.setRenter(exRenter);
        reqModel.setVendor(exVendor);
        reqModel.setStatus(Booking.STATUS.INCOMING);
        reqModel.setCreatedAt(System.currentTimeMillis());


        if (reqModel.getQrCode() == null) {
            String qrCode = generateQRCode();
            reqModel.setQrCode(qrCode);
        }

        return bookingRepository.save(reqModel);
    }

    @Override
    public Booking update(BookingDTOShort reqDTO) {
//        checkActive(reqDTO.getBookingId());

        if (bookingRepository.existsById(reqDTO.getBookingId())){
            // Update status
            Booking exModel = bookingRepository.getOne(reqDTO.getBookingId());
            if (!exModel.getStatus().equals(reqDTO.getStatus())) {
                exModel.setStatus(reqDTO.getStatus());
                setUpdatedTime(exModel);
                return bookingRepository.save(exModel);
            }
            // Update meetTime
            //TODO: Implement here
        }else {
            throw new EntityNotFoundException(Booking.class, "id", reqDTO.getBookingId().toString());
        }

        return null;
    }

    /**
     * Get list of bookings by given renter id
     *
     * @param renterId given renter id to get booking
     * @return list of booking models
     */
    @Override
    public List<Booking> findByRenterId(Long renterId) {
//        renterService.checkExist(renterId);
        if (renterRepository.existsById(renterId)) {
            return renterRepository.findById(renterId).get().getBookings();
        }else{
            throw new EntityNotFoundException(Renter.class, "id", renterId.toString());
        }
    }

    /**
     * Get list of bookings by given vendor id
     * @param vendorId given vendor id to get booking
     * @return list of booking models
     */
    @Override
    public List<Booking> findByVendorId(Long vendorId) {
//        vendorService.checkExist(vendorId);
        if(vendorRepository.existsById(vendorId)) {
            return vendorRepository.findById(vendorId).get().getBookings();
        }else{
            throw new EntityNotFoundException(Vendor.class, "id", vendorId.toString());
        }
    }

    @Override
    public Booking changeStatus(Integer id, Booking.STATUS status) {
        Optional<Booking> existed = bookingRepository.findById(id);
        if (existed.isPresent() && existed.get().getStatus().equals(Booking.STATUS.INCOMING)){
            existed.get().setStatus(status);
            setUpdatedTime(existed.get());
        }
        return existed.orElse(null);
    }


    private String generateQRCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    private void setUpdatedTime(Booking exModel){
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
