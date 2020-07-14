package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.BookingStatusDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.model.BookingStatus;
import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.avengers.capstone.hostelrenting.service.BookingStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.CREATE_SUCCESS;
import static org.avengers.capstone.hostelrenting.Constant.Message.GET_SUCCESS;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class BookingStatusController {
    private ModelMapper modelMapper;
    private BookingStatusService bookingStatusService;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBookingStatusService(BookingStatusService bookingStatusService) {
        this.bookingStatusService = bookingStatusService;
    }

    @PostMapping("/bookingstatuses")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid BookingStatusDTO reqDTO){
        BookingStatus rqModel = modelMapper.map(reqDTO, BookingStatus.class);
        BookingStatus createdModel = bookingStatusService.save(rqModel);
        BookingStatusDTO responseDTO = modelMapper.map(createdModel, BookingStatusDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, BookingStatus.class.getSimpleName())));
    }

    @GetMapping("/bookingstatuses")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam (required = false) String statusName,
                                             @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<BookingStatusDTO> results = bookingStatusService.findAll()
                .stream()
                .filter(status -> {
                    if (status != null)
                        return status.getStatusName().toLowerCase().contains(statusName.trim().toLowerCase());
                    return true;
                }).skip((page-1) * size)
                .limit(size)
                .map(status -> modelMapper.map(status, BookingStatusDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no status"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess( results, String.format(GET_SUCCESS, BookingStatus.class.getSimpleName())));
    }

}
