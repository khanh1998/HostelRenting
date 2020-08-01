package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private VendorService vendorService;
    private RenterService renterService;
    private HostelTypeService hostelTypeService;
    private DealService dealService;

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
    public Booking findById(Integer id) {
        if (isNotFound(id))
            throw new EntityNotFoundException(Booking.class, "id", id.toString());

        return bookingRepository.getOne(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createNew(Booking booking) {
        Vendor existedVendor = vendorService.findById(booking.getVendor().getUserId());
        Renter existedRenter = renterService.findById(booking.getRenter().getUserId());
        HostelType existedType = hostelTypeService.findById(booking.getHostelType().getTypeId());

        booking.setHostelType(existedType);
        booking.setRenter(existedRenter);
        booking.setVendor(existedVendor);
        booking.setStatus(Booking.Status.INCOMING);

        if (booking.getQrCode() == null) {
            String qrCode = generateQRCode();
            booking.setQrCode(qrCode);
        }

        Integer dealId = booking.getDealId();
        if (dealId != null) {
            dealService.changeStatus(dealId, Deal.Status.DONE);
        }

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
        }
        bookingRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.isEmpty();
    }

    private String generateQRCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
