package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class DistrictController {
    private DistrictService districtService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/districts")
    public ResponseEntity<ApiSuccess> getAllDistrict() {
        List<District> results = districtService.findAll()
                .stream()
                .map(district -> modelMapper.map(district, District.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no district"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "District")));
    }

    @GetMapping("/districts/{districtId}")
    public ResponseEntity<ApiSuccess> getDistrictById(@PathVariable Integer districtId) throws EntityNotFoundException {
        District district = districtService.findById(districtId);
        DistrictDTO districtDTO = modelMapper.map(district, DistrictDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(districtDTO, String.format(GET_SUCCESS, "District")));
    }

    @PostMapping("/districts")
    public ResponseEntity<ApiSuccess> createDistrict(@Valid @RequestBody DistrictDTO districtDTO) throws DuplicateKeyException {

        District district = modelMapper.map(districtDTO, District.class);
        District createdDistrict = districtService.save(district);
        districtDTO = modelMapper.map(createdDistrict, DistrictDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(districtDTO, String.format(CREATE_SUCCESS, "District")));
    }

    @PutMapping("/districts/{districtId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer districtId, @RequestBody DistrictDTO districtDTO) throws EntityNotFoundException {
        District oldDistrict = modelMapper.map(districtDTO, District.class);
        oldDistrict.setDistrictId(districtId);
        DistrictDTO updatedDistrict = modelMapper.map(districtService.save(oldDistrict), DistrictDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedDistrict, String.format(UPDATE_SUCCESS, "District")));
    }

    @DeleteMapping("/districts/{districtId}")
    public ResponseEntity<ApiSuccess> deleteDistrict(@PathVariable Integer districtId) throws EntityNotFoundException{
        districtService.delete(districtId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
