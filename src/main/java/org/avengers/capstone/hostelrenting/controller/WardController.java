package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.WardDTO;
import org.avengers.capstone.hostelrenting.dto.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.avengers.capstone.hostelrenting.service.DistrictService;
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
@RequestMapping("api/v1")
public class WardController {

    private WardService wardService;
    private DistrictService districtService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }

    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<ApiSuccess> getWardsByDistrictId(@PathVariable Integer districtId)throws EntityNotFoundException {
        District district = districtService.findById(districtId);
        district.getWards();
        return ResponseEntity.status(HttpStatus.OK).body( new ApiSuccess(modelMapper.map(district, DistrictDTO.class), String.format(GET_SUCCESS, "Ward")));
    }

    @GetMapping("/districts/{districtId}/wards/{wardId}")
    public ResponseEntity<ApiSuccess> getWardByDistrictId(@PathVariable Integer districtId, @PathVariable Integer wardId)throws EntityNotFoundException{
        District districtModel = districtService.findById(districtId);
        Ward responseModel = districtModel.getWards()
                .stream()
                .filter(p -> p.getWardId() == wardId)
                .collect(Collectors.toList())
                .get(0);
        WardDTO responseDTO = modelMapper.map(responseModel, WardDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body( new ApiSuccess(responseDTO, String.format(GET_SUCCESS, "Ward")));
    }

    @PostMapping("districts/{districtId}/wards")
    public ResponseEntity<ApiSuccess> createWard(@PathVariable Integer districtId,
                                                     @Valid @RequestBody WardDTO rqWard) throws DuplicateKeyException {
        District districtModel = districtService.findById(districtId);
        Ward model = modelMapper.map(rqWard, Ward.class);
        model.setDistrict(districtModel);
        wardService.save(model);
        WardDTO createdDTO = modelMapper.map(model, WardDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, "Ward")));
    }

    @PutMapping("/districts/{districtId}/wards/{wardId}")
    public ResponseEntity<ApiSuccess> updateWard(@PathVariable Integer districtId,
                                                     @PathVariable Integer wardId,
                                                     @Valid @RequestBody WardDTO rqWard) throws EntityNotFoundException {
        rqWard.setWardId(wardId);
        rqWard.setDistrictId(districtId);
        Ward existedModel = modelMapper.map(rqWard, Ward.class);
        Ward updatedModel = wardService.save(existedModel);
        WardDTO updatedDTO = modelMapper.map(updatedModel, WardDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, "Ward")));
    }

    @DeleteMapping("/districts/{districtId}/wards/{wardId}")
    public ResponseEntity<ApiSuccess> deleteWard(@PathVariable Integer districtId,
                                                     @PathVariable Integer wardId) throws EntityNotFoundException{


        Ward existedModel = wardService.findByIdAndDistrictId(wardId, districtId);
        wardService.deleteById(existedModel.getWardId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
