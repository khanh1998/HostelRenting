package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOResponse;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private BookingService bookingService;
    private GroupService groupService;
    private DealService dealService;
    private VendorService vendorService;
    private RenterService renterService;
    private TypeService typeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setHostelTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

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
    public ResponseEntity<?> createBooking(@RequestBody @Valid BookingDTOCreate reqDTO) {
        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
        Renter exRenter = renterService.findById(reqDTO.getRenterId());
        Type exType = typeService.findById(reqDTO.getTypeId());
        Booking reqModel = modelMapper.map(reqDTO, Booking.class);
        reqModel = reqModel.toBuilder()
                .vendor(exVendor)
                .renter(exRenter)
                .type(exType)
                .build();

        Booking resModel = bookingService.create(reqModel);

        BookingDTOResponse resDTO = modelMapper.map(resModel, BookingDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<?> updateBookingById(@PathVariable Integer bookingId,
                                           @Valid @RequestBody BookingDTOUpdate reqDTO) {

        String resMsg = "Your booking has been updated";
        Booking exModel = bookingService.findById(bookingId);
        Booking resModel = bookingService.confirm(exModel, reqDTO);
        BookingDTOResponse resDTO = modelMapper.map(resModel, BookingDTOResponse.class);
        if (resDTO == null)
            resMsg = "Your booking is up to date";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer bookingId) {
        Booking resModel = bookingService.findById(bookingId);
        BookingDTOResponse resDTO = modelMapper.map(resModel, BookingDTOResponse.class);

        // get group
        getGroupForDTO(resDTO);
        if (resDTO.getDeal() != null)
            getDealForDTO(resDTO);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/bookings")
    public ResponseEntity<?> getBookingsByRenterId(@PathVariable UUID renterId) throws EntityNotFoundException {
        String resMsg = "Your booking(s) has been retrieved successfully!";

        List<BookingDTOResponse> resDTOs = bookingService.findByRenterId(renterId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOResponse.class))
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
    public ResponseEntity<?> getBookingsByVendorId(@PathVariable UUID vendorId) throws EntityNotFoundException {
        String resMsg = "Your booking(s) has been retrieved successfully!";

        List<BookingDTOResponse> resDTOs = bookingService.findByVendorId(vendorId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTOResponse.class))
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
    private void getDealForDTOs(List<BookingDTOResponse> resDTOs) {
        resDTOs.forEach(dto -> {
            if (dto.getDeal() != null) {
                Deal existedDeal = dealService.findById(dto.getDeal().getDealId());
                dto.setDeal(modelMapper.map(existedDeal, DealDTOResponseShort.class));
            }
        });
    }

    /**
     * Get corresponding {@link Group}
     *
     * @param resDTOs list of {@link Booking} need to fill {@link Group}
     */
    private void getGroupForDTOs(List<BookingDTOResponse> resDTOs) {
        resDTOs.forEach(resDTO -> {
            Group existedGroup = groupService.findById(resDTO.getType().getGroupId());
            resDTO.setGroup(modelMapper.map(existedGroup, GroupDTOResponse.class));
        });
    }

    private void getGroupForDTO(BookingDTOResponse resDTO) {
        Group existedGroup = groupService.findById(resDTO.getType().getGroupId());
        resDTO.setGroup(modelMapper.map(existedGroup, GroupDTOResponse.class));
    }

    private void getDealForDTO(BookingDTOResponse resDTO) {
        if (resDTO.getDeal() != null) {
            Deal existedDeal = dealService.findById(resDTO.getDeal().getDealId());
            resDTO.setDeal(modelMapper.map(existedDeal, DealDTOResponseShort.class));
        }
    }
}
