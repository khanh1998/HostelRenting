package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private VendorService vendorService;
    private RenterService renterService;
    private HostelTypeService hostelTypeService;
    private DealService dealService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void checkActive(Integer id) {
        Optional<Booking> model = bookingRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
    }

    @Override
    public Booking findById(Integer id) {
        checkActive(id);
        return bookingRepository.getOne(id);

    }

    @Override
    public Booking create(BookingDTOShort reqDTO) {
        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
        Renter exRenter = renterService.findById(reqDTO.getRenterId());
        HostelType exType = hostelTypeService.findById(reqDTO.getTypeId());

        Booking reqModel = modelMapper.map(reqDTO, Booking.class);

        // Update deal status if exist (booking created means deal is done)
        Integer dealId = reqDTO.getDealId();
        if (dealId != null) {
            dealService.checkActive(dealId);
            dealService.changeStatus(dealId, Deal.STATUS.DONE);
        }

        reqModel.setHostelType(exType);
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
        checkActive(reqDTO.getBookingId());

        // Update status
        Booking exModel = bookingRepository.getOne(reqDTO.getBookingId());
        if (!exModel.getStatus().equals(reqDTO.getStatus())) {
            exModel.setStatus(reqDTO.getStatus());
            setUpdatedTime(exModel);
            return bookingRepository.save(exModel);
        }
        // Update meetTime
        //TODO: Implement here

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
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getBookings();
    }

    /**
     * Get list of bookings by given vendor id
     * @param vendorId given vendor id to get booking
     * @return list of booking models
     */
    @Override
    public List<Booking> findByVendorId(Long vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getBookings();
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
