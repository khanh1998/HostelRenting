package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.BookingDTO;
import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.dto.VendorDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.awt.print.Book;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
    private ModelMapper modelMapper;
    private BookingService bookingService;
    private RenterService renterService;
    private VendorService vendorService;
    private HostelTypeService hostelTypeService;
    private BookingStatusService bookingStatusService;

    @Autowired
    public void setBookingStatusService(BookingStatusService bookingStatusService) {
        this.bookingStatusService = bookingStatusService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid BookingDTO reqDTO){
        // check that vendor, renter and type id is existed or not
        Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
        Renter existedRenter = renterService.findById(reqDTO.getRenterId());
        HostelType existedType = hostelTypeService.findById(reqDTO.getTypeId());

        Booking reqModel = modelMapper.map(reqDTO, Booking.class);
        reqModel.setHostelType(existedType);
        reqModel.setVendor(existedVendor);
        reqModel.setRenter(existedRenter);

        Booking resModel = bookingService.save((reqModel));
        BookingDTO resDTO = modelMapper.map(resModel, BookingDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Booking.class.getSimpleName())));
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiSuccess> updateBooking(@PathVariable Integer bookingId,
                                                    @Valid @RequestBody BookingDTO reqDTO){
        Booking existedModel = bookingService.findById(bookingId);
        existedModel.setStatus(bookingStatusService.findById(reqDTO.getStatusId()));
        Booking resModel = bookingService.save(existedModel);
        BookingDTO resDTO = modelMapper.map(resModel, BookingDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Booking.class.getSimpleName())));
    }

    @GetMapping("/renters/{renterId}/bookings")
    public ResponseEntity<ApiSuccess> getByRenterId(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedRenter = renterService.findById(renterId);
        existedRenter.getBookings();
        RenterDTO resDTO = modelMapper.map(existedRenter, RenterDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/vendors/{vendorId}/bookings")
    public ResponseEntity<ApiSuccess> getByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedVendor = vendorService.findById(vendorId);
        existedVendor.getBookings();
        VendorDTO resDTO = modelMapper.map(existedVendor, VendorDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Vendor.class.getSimpleName())));
    }
}
