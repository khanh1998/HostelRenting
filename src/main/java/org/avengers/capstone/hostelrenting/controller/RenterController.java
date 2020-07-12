package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.dto.VendorDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.RenterService;
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
public class RenterController {
    private ModelMapper modelMapper;
    private RenterService renterService;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping("/renters")
    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody RenterDTO reqDTO){
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        Renter createdModel = renterService.save(reqModel);

        RenterDTO resDTO = modelMapper.map(createdModel, RenterDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/renters")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam(required = false) String username,
                                             @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<RenterDTO> results = renterService.findAll()
                .stream()
                .filter(renter -> {
                    if (username != null)
                        return renter.getUsername().toLowerCase().contains(username.trim().toLowerCase());
                    return true;
                }).skip((page-1) * size)
                .limit(size)
                .map(renter -> modelMapper.map(renter, RenterDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no renter"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(results, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        RenterDTO resDTO = modelMapper.map(existedModel, RenterDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }


}
