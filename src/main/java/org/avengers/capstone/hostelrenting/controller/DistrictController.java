package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.district.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;
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

import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class DistrictController {
    private DistrictService districtService;
    private ProvinceService provinceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<ApiSuccess> getDistrictsByProvinceId(@PathVariable Integer provinceId)throws EntityNotFoundException{
        Province province = provinceService.findById(provinceId);
        province.getDistricts();
        return ResponseEntity.status(HttpStatus.OK).body( new ApiSuccess(modelMapper.map(province, ProvinceDTO.class), String.format(GET_SUCCESS, "District")));
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> getDistrictByProvinceId(@PathVariable Integer provinceId, @PathVariable Integer districtId)throws EntityNotFoundException{
        Province provinceModel = provinceService.findById(provinceId);
        District responseModel = provinceModel.getDistricts()
                .stream()
                .filter(p -> p.getDistrictId() == districtId)
                .collect(Collectors.toList())
                .get(0);
        DistrictDTO responseDTO = modelMapper.map(responseModel, DistrictDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body( new ApiSuccess(responseDTO, String.format(GET_SUCCESS, "District")));
    }

    @PostMapping("provinces/{provinceId}/districts")
    public ResponseEntity<ApiSuccess> createDistrict(@PathVariable Integer provinceId,
                                                     @Valid @RequestBody DistrictDTO rqDistrict) throws DuplicateKeyException{
        Province provinceModel = provinceService.findById(provinceId);
        District model = modelMapper.map(rqDistrict, District.class);
        model.setProvince(provinceModel);
        districtService.save(model);
        DistrictDTO createdDTO = modelMapper.map(model, DistrictDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, "District")));
    }

    @PutMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer provinceId,
                                                     @PathVariable Integer districtId,
                                                     @Valid @RequestBody DistrictDTO rqDistrict) throws EntityNotFoundException {
        rqDistrict.setDistrictId(districtId);
        rqDistrict.setProvinceId(modelMapper.map(provinceService.findById(provinceId), ProvinceDTO.class));
        District existedModel = modelMapper.map(rqDistrict, District.class);
        District updatedModel = districtService.save(existedModel);
        DistrictDTO updatedDTO = modelMapper.map(updatedModel, DistrictDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, "District")));
    }

    @DeleteMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> deleteDistrict(@PathVariable Integer provinceId,
                                                     @PathVariable Integer districtId) throws EntityNotFoundException{


        District existedModel = districtService.findByIdAndProvinceId(districtId, provinceId);
        districtService.delete(existedModel.getDistrictId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }


}
