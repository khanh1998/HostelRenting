package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.dto.user.UserIdDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Arrays;
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

//
//    @PostMapping("/renters/register")
//    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody RenterDTOFull reqDTO){
//        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
//        Renter createdModel = renterService.save(reqModel);
//
//        RenterDTOFull resDTO = modelMapper.map(createdModel, RenterDTOFull.class);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Renter.class.getSimpleName())));
//    }


    @GetMapping("/renters/{renterId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        UserDTOFull resDTO = modelMapper.map(existedModel, UserDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/renters")
    public ResponseEntity<ApiSuccess> getRenterByIds(@RequestParam Integer[] renterIds){
        List<UserDTOFull> resDTOs = Arrays.stream(renterIds)
                .map(id -> modelMapper.map(renterService.findById(id), UserDTOFull.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTOs, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

}
