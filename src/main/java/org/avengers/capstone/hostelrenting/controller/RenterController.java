package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOLogin;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
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

    @PostMapping("/renters/login")
    public ResponseEntity<ApiSuccess> loginRenter(@Valid @RequestBody VendorDTOLogin reqDTO){
        Renter matchedRenter = renterService.checkLogin(reqDTO.getPhone(), reqDTO.getPassword());
        if (matchedRenter != null){
            RenterDTOFull resDTO = modelMapper.map(matchedRenter, RenterDTOFull.class);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiSuccess(resDTO, "Login successfully!"));
        }else{
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiSuccess( "Invalid phone or password", false));
        }
    }

    @PostMapping("/renters/register")
    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody RenterDTOFull reqDTO){
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        Renter createdModel = renterService.save(reqModel);

        RenterDTOFull resDTO = modelMapper.map(createdModel, RenterDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/renters")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam(required = false) String username,
                                             @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<RenterDTOFull> results = renterService.findAll()
                .stream()
                .filter(renter -> {
                    if (username != null)
                        return renter.getUsername().toLowerCase().contains(username.trim().toLowerCase());
                    return true;
                }).skip((page-1) * size)
                .limit(size)
                .map(renter -> modelMapper.map(renter, RenterDTOFull.class))
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
        RenterDTOFull resDTO = modelMapper.map(existedModel, RenterDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }


}
