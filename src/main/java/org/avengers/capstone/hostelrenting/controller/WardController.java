package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOCreate;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOResponse;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOCreate;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class WardController {

    private DistrictService districtService;
    private WardService wardService;
    private ModelMapper modelMapper;

    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWardService() {
    }

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping("/wards")
    public ResponseEntity<?> createNewWards(@Validated @RequestBody Collection<WardDTOCreate> reqDTOs) {

        // convert dto -> model
        Collection<Ward> reqModels = reqDTOs.stream().map(wardDTOCreate -> {
            Ward model = modelMapper.map(wardDTOCreate, Ward.class);
            // get existed disrtict
            District exDistrict = districtService.findById(wardDTOCreate.getDistrictId());
            // set district for ward
            model.setDistrict(exDistrict);
            return model;
        }).collect(Collectors.toList());

        // save and map to dto for responding
        Collection<WardDTOResponse> resDTOs = reqModels.stream().map(ward -> {
                    Ward resModel = wardService.create(ward);
                    return modelMapper.map(resModel, WardDTOResponse.class);
                }
        ).collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Wards has been created successfully!");
        apiSuccess.setTotal(resDTOs.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<?> getWardsByDistrictId(@PathVariable Integer districtId) throws EntityNotFoundException {
        District district = districtService.findById(districtId);
        List<WardDTOResponse> wardDTOResponses = district.getWards()
                .stream()
                .map(ward -> modelMapper.map(ward, WardDTOResponse.class))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(wardDTOResponses, "Wards has been retrieved successfully!");
        return ResponseEntity.ok(apiSuccess);
    }

    @GetMapping("/districts/{districtId}/wards/{wardId}")
    public ResponseEntity<?> getWardByDistrictId(@PathVariable Integer districtId, @PathVariable Integer wardId) throws EntityNotFoundException {
        District districtModel = districtService.findById(districtId);
        Ward responseModel = districtModel.getWards()
                .stream()
                .filter(p -> p.getWardId() == wardId)
                .collect(Collectors.toList())
                .get(0);
        WardDTOResponse resDTO = modelMapper.map(responseModel, WardDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Ward has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
