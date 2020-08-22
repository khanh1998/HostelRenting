package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.WardDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class WardController {

    private DistrictService districtService;
    private ModelMapper modelMapper;

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

    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<?> getWardsByDistrictId(@PathVariable Integer districtId) throws EntityNotFoundException {
        District district = districtService.findById(districtId);
        List<WardDTO> wardDTOS = district.getWards()
                .stream()
                .map(ward -> modelMapper.map(ward, WardDTO.class))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(wardDTOS, "Wards has been retrieved successfully!");
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
        WardDTO resDTO = modelMapper.map(responseModel, WardDTO.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Ward has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
