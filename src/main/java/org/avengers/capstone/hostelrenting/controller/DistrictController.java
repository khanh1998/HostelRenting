package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
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
public class DistrictController {
    private ProvinceService provinceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<?> getDistrictsByProvinceId(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province province = provinceService.findById(provinceId);
        List<DistrictDTOFull> districtDTOs = province.getDistricts()
                .stream()
                .map((district -> modelMapper.map(district, DistrictDTOFull.class)))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(districtDTOs, "Districts has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<?> getDistrictByProvinceId(@PathVariable Integer provinceId,
                                                              @PathVariable Integer districtId) throws EntityNotFoundException {
        Province provinceModel = provinceService.findById(provinceId);
        District responseModel = provinceModel.getDistricts()
                .stream()
                .filter(p -> p.getDistrictId() == districtId)
                .collect(Collectors.toList())
                .get(0);
        DistrictDTOFull resDTO = modelMapper.map(responseModel, DistrictDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "District has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
