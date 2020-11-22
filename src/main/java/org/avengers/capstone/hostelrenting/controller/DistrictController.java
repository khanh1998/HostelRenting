package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.district.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOCreate;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
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
public class DistrictController {
    private ProvinceService provinceService;
    private DistrictService districtService;
    private ModelMapper modelMapper;

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/districts")
    public ResponseEntity<?> createNewDistricts(@Validated @RequestBody Collection<DistrictDTOCreate> reqDTOs) throws DuplicateKeyException {

        // convert dto -> model
        Collection<District> reqModels = reqDTOs.stream().map(districtDTOCreate -> {
            District model = modelMapper.map(districtDTOCreate, District.class);
            // get existed Province
            Province exProvince = provinceService.findById(districtDTOCreate.getProvinceId());
            // set province for district
            model.setProvince(exProvince);
            return model;
        }).collect(Collectors.toList());

        // save and map to dto for responding
        Collection<DistrictDTOResponse> resDTOs = reqModels.stream().map(district -> {
                    District resModel = districtService.create(district);
                    return modelMapper.map(resModel, DistrictDTOResponse.class);
                }
        ).collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Districts has been created successfully!");
        apiSuccess.setTotal(resDTOs.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<?> getDistrictsByProvinceId(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province province = provinceService.findById(provinceId);
        List<DistrictDTOResponse> districtDTOs = province.getDistricts()
                .stream()
                .map((district -> modelMapper.map(district, DistrictDTOResponse.class)))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(districtDTOs, "Districts has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<?> getDistrictByDistrictId(@PathVariable Integer provinceId,
                                                     @PathVariable Integer districtId) throws EntityNotFoundException {
        Province provinceModel = provinceService.findById(provinceId);
        District responseModel = provinceModel.getDistricts()
                .stream()
                .filter(p -> p.getDistrictId() == districtId)
                .collect(Collectors.toList())
                .get(0);
        DistrictDTOResponse resDTO = modelMapper.map(responseModel, DistrictDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "District has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

}
