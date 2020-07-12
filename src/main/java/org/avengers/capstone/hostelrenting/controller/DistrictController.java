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
    public ResponseEntity<ApiSuccess> getDistrictsByProvinceId(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province province = provinceService.findById(provinceId);
        province.getDistricts();
        ProvinceDTO resDTO = modelMapper.map(province, ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, District.class.getSimpleName())));
    }

    @GetMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> getDistrictByProvinceId(@PathVariable Integer provinceId,
                                                              @PathVariable Integer districtId) throws EntityNotFoundException {
        Province provinceModel = provinceService.findById(provinceId);
        District responseModel = provinceModel.getDistricts()
                .stream()
                .filter(p -> p.getDistrictId() == districtId)
                .collect(Collectors.toList())
                .get(0);
        DistrictDTO resDTO = modelMapper.map(responseModel, DistrictDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, District.class.getSimpleName())));
    }

    @PostMapping("provinces/{provinceId}/districts")
    public ResponseEntity<ApiSuccess> createDistrict(@PathVariable Integer provinceId,
                                                     @Valid @RequestBody DistrictDTO rqDistrict) throws DuplicateKeyException {
        Province provinceModel = provinceService.findById(provinceId);
        District model = modelMapper.map(rqDistrict, District.class);
        model.setProvince(provinceModel);
        District createdModel = districtService.save(model);
        DistrictDTO createdDTO = modelMapper.map(createdModel, DistrictDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, District.class.getSimpleName())));
    }

    @PutMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer provinceId,
                                                     @PathVariable Integer districtId,
                                                     @Valid @RequestBody DistrictDTO reqDTO) throws EntityNotFoundException {
        // check that id exist or not
        Province existedProvince = provinceService.findById(provinceId);
        District existedDistrict = districtService.findById(districtId);

        reqDTO.setDistrictId(existedDistrict.getDistrictId());
        District rqModel = modelMapper.map(reqDTO, District.class);
        rqModel.setProvince(existedProvince);
        District updatedModel = districtService.save(rqModel);
        DistrictDTO updatedDTO = modelMapper.map(updatedModel, DistrictDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, District.class.getSimpleName())));
    }

    @DeleteMapping("/provinces/{provinceId}/districts/{districtId}")
    public ResponseEntity<ApiSuccess> deleteDistrict(@PathVariable Integer provinceId,
                                                     @PathVariable Integer districtId) throws EntityNotFoundException {


        District existedModel = districtService.findByIdAndProvinceId(districtId, provinceId);
        districtService.deleteById(existedModel.getDistrictId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }


}
