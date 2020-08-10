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
        else if (model.get().isDeleted())
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
    }

    @Override
    public Booking findById(Integer id) {
        checkActive(id);

        return bookingRepository.getOne(id);
    }

    @Override
    public Booking create(BookingDTOShort reqDTO) {
        Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
        Renter existedRenter = renterService.findById(reqDTO.getRenterId());
        HostelType existedType = hostelTypeService.findById(reqDTO.getTypeId());

        Booking reqModel = modelMapper.map(reqDTO, Booking.class);
        Integer dealId = reqDTO.getDealId();
        if (dealId != null) {
            dealService.checkActive(dealId);
            dealService.changeStatus(dealId, Deal.Status.DONE);
        }


        reqModel.setHostelType(existedType);
        reqModel.setRenter(existedRenter);
        reqModel.setVendor(existedVendor);
        reqModel.setStatus(Booking.Status.INCOMING);
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

    @Override
    public void delete(Integer id) {
        checkActive(id);
        Booking exModel = bookingRepository.getOne(id);
        if (exModel.isDeleted())
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
        exModel.setDeleted(true);
        setUpdatedTime(exModel);
        bookingRepository.save(exModel);
    }

    @Override
    public List<Booking> findByRenterId(Integer renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getBookings().stream().filter(booking -> !booking.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByVendorId(Integer vendorId) {
        vendorService.checkExist(vendorId);

        return renterService.findById(vendorId).getBookings().stream().filter(booking -> !booking.isDeleted()).collect(Collectors.toList());
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
