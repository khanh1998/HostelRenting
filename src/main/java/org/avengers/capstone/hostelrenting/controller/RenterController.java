package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.GET_SUCCESS;

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

    @PutMapping("/renters/{renterId}")
    public ResponseEntity<?> update(@PathVariable Integer renterId,
                                    @RequestBody RenterDTOFull reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(renterId);
        User resModel = renterService.update(reqDTO);
        RenterDTOFull resDTO = modelMapper.map(resModel, RenterDTOFull.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        RenterDTOFull resDTO = modelMapper.map(existedModel, RenterDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/renters")
    public ResponseEntity<ApiSuccess> getRenterByIds(@RequestParam Integer[] renterIds){
        List<RenterDTOFull> resDTOs = Arrays.stream(renterIds)
                .map(id -> modelMapper.map(renterService.findById(id), RenterDTOFull.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTOs, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

}
