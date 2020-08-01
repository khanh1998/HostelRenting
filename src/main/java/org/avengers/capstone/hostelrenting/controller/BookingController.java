package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOFull;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
    private ModelMapper modelMapper;
    private BookingService bookingService;
    private RenterService renterService;
    private VendorService vendorService;
    private HostelGroupService hostelGroupService;


    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
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


    @PostMapping("/bookings")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid BookingDTOShort reqDTO){

        Booking reqModel = modelMapper.map(reqDTO, Booking.class);

        Booking resModel = bookingService.createNew((reqModel));

        BookingDTOShort resDTO = modelMapper.map(resModel, BookingDTOShort.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Booking.class.getSimpleName())));
    }

//    @PutMapping("/bookings/{bookingId}")
//    public ResponseEntity<ApiSuccess> updateBooking(@PathVariable Integer bookingId,
//                                                    @Valid @RequestBody BookingDTOShort reqDTO){
//        Booking existedModel = bookingService.findById(bookingId);
//        existedModel.setStatus(reqDTO.getStatus());
//        Booking resModel = bookingService.save(existedModel);
//        BookingDTOShort resDTO = modelMapper.map(resModel, BookingDTOShort.class);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Booking.class.getSimpleName())));
//    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiSuccess> getBookingById(@PathVariable Integer bookingId) throws EntityNotFoundException{
        Booking existedModel = bookingService.findById(bookingId);
        BookingDTOFull resDTO = modelMapper.map(existedModel, BookingDTOFull.class);
        HostelGroup existedGroup = hostelGroupService.findById(resDTO.getType().getGroupId());
        resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Booking.class.getSimpleName())));
    }

    @GetMapping("/renters/{renterId}/bookings")
    public ResponseEntity<ApiSuccess> getByRenterId(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedRenter = renterService.findById(renterId);
        List<BookingDTOFull> resBookings = existedRenter.getBookings()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());

        resBookings.stream().forEach(resDTO -> {
            HostelGroup existedGroup = hostelGroupService.findById(resDTO.getType().getGroupId());
            resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));
        });


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resBookings, String.format(GET_SUCCESS, Booking.class.getSimpleName())));
    }

    @GetMapping("/vendors/{vendorId}/bookings")
    public ResponseEntity<ApiSuccess> getByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedVendor = vendorService.findById(vendorId);
        List<BookingDTOFull> resBookings = existedVendor.getBookings()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());

        resBookings.stream().forEach(resDTO -> {
            HostelGroup existedGroup = hostelGroupService.findById(resDTO.getType().getGroupId());
            resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));
        });

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resBookings, String.format(GET_SUCCESS, Booking.class.getSimpleName())));
    }
}
