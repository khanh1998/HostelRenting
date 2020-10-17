package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOFull;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.GroupService;
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
    private GroupService groupService;
    private DealService dealService;
    private ModelMapper modelMapper;

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> create(@RequestBody @Valid BookingDTOShort reqDTO) {

        Booking resModel = bookingService.create(reqDTO);
        BookingDTOFull resDTO = modelMapper.map(resModel, BookingDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer bookingId,
                                           @Valid @RequestBody BookingDTOShort reqDTO) {

        String resMsg = "Your booking has been updated";
        reqDTO.setBookingId(bookingId);
        Booking resModel = bookingService.update(reqDTO);
        BookingDTOFull resDTO = modelMapper.map(resModel, BookingDTOFull.class);
        if (resDTO == null)
            resMsg = "Your booking is up to date";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer bookingId) {
        Booking resModel = bookingService.findById(bookingId);
        BookingDTOFull resDTO = modelMapper.map(resModel, BookingDTOFull.class);

        // get group
        getGroupForDTO(resDTO);
        if (resDTO.getDeal() != null)
            getDealForDTO(resDTO);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/bookings")
    public ResponseEntity<?> getByRenterId(@PathVariable Long renterId) throws EntityNotFoundException {
        String resMsg = "Your booking(s) has been retrieved successfully!";

        List<BookingDTOFull> resDTOs = bookingService.findByRenterId(renterId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no booking";

        getGroupForDTOs(resDTOs);
        getDealForDTOs(resDTOs);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/bookings")
    public ResponseEntity<?> getByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        String resMsg = "Your booking(s) has been retrieved successfully!";

        List<BookingDTOFull> resDTOs = bookingService.findByVendorId(vendorId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOFull.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no booking";

        getGroupForDTOs(resDTOs);
        getDealForDTOs(resDTOs);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    /**
     * Get corresponding {@link Deal}
     *
     * @param resDTOs list of {@link Booking} need to fill {@link Group}
     */
    private void getDealForDTOs(List<BookingDTOFull> resDTOs) {
        resDTOs.forEach(dto -> {
            if (dto.getDeal() != null) {
                Deal existedDeal = dealService.findById(dto.getDeal().getDealId());
                dto.setDeal(modelMapper.map(existedDeal, DealDTOShort.class));
            }
        });
    }

    /**
     * Get corresponding {@link Group}
     *
     * @param resDTOs list of {@link Booking} need to fill {@link Group}
     */
    private void getGroupForDTOs(List<BookingDTOFull> resDTOs) {
        resDTOs.forEach(resDTO -> {
            Group existedGroup = groupService.findById(resDTO.getType().getGroupId());
            resDTO.setGroup(modelMapper.map(existedGroup, GroupDTOResponse.class));
        });
    }

    private void getGroupForDTO(BookingDTOFull resDTO) {
        Group existedGroup = groupService.findById(resDTO.getType().getGroupId());
        resDTO.setGroup(modelMapper.map(existedGroup, GroupDTOResponse.class));
    }

    private void getDealForDTO(BookingDTOFull resDTO) {
        if (resDTO.getDeal() != null) {
            Deal existedDeal = dealService.findById(resDTO.getDeal().getDealId());
            resDTO.setDeal(modelMapper.map(existedDeal, DealDTOShort.class));
        }
    }
}
