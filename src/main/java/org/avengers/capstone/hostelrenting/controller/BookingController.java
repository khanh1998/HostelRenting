package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOFull;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private BookingService bookingService;
    private HostelGroupService hostelGroupService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> create(@RequestBody @Valid BookingDTOShort reqDTO) {

        BookingDTOShort resDTO = modelMapper.map(bookingService.create((reqDTO)), BookingDTOShort.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer bookingId,
                                                    @Valid @RequestBody BookingDTOShort reqDTO) {

        String resMsg = "Your booking has been updated";
        reqDTO.setBookingId(bookingId);
        BookingDTOShort resDTO = modelMapper.map(bookingService.update(reqDTO), BookingDTOShort.class);
        if (resDTO == null)
            resMsg = "Your booking is up to date";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer bookingId) throws EntityNotFoundException{

        BookingDTOFull resDTO = modelMapper.map(bookingService.findById(bookingId), BookingDTOFull.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/bookings")
    public ResponseEntity<?> getByRenterId(@PathVariable Integer renterId) throws EntityNotFoundException {
        List<BookingDTOFull> resBookings = bookingService.findByRenterId(renterId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(getGroupForBooking(resBookings),
                "Your booking(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/bookings")
    public ResponseEntity<?> getByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        List<BookingDTOFull> resBookings = bookingService.findByVendorId(vendorId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());


        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(getGroupForBooking(resBookings),
                "Your booking(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    /**
     * Get corresponding {@link HostelGroup}
     *
     * @param bookings list of {@link org.avengers.capstone.hostelrenting.model.Booking} need to fill {@link HostelGroup}
     * @return list of {@link org.avengers.capstone.hostelrenting.model.Booking} with corresponding {@link HostelGroup}
     */
    private List<BookingDTOFull> getGroupForBooking(List<BookingDTOFull> bookings){
        bookings.forEach(resDTO -> {
            HostelGroup existedGroup = hostelGroupService.findById(resDTO.getType().getGroupId());
            resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));
        });
        return bookings;
    }
}
