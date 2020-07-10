package org.avengers.capstone.hostelrenting.controller;


import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.service.FacilityService;
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
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class FacilityController {
    private FacilityService facilityService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * create a Facility with a request facility obj
     *
     * @param reqDTO
     * @return reqDTO
     * @throws DuplicateKeyException
     */
    @PostMapping("/facilities")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid FacilityDTO reqDTO) throws DuplicateKeyException {
        Facility rqModel = modelMapper.map(reqDTO, Facility.class);
        Facility createdModel = facilityService.save(rqModel);
        FacilityDTO responseDTO = modelMapper.map(createdModel, FacilityDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Facility.class.getSimpleName())));
    }

    /**
     * Filter and get list all of provinces
     *
     * @param facilityName
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/facilities")
    public ResponseEntity<ApiSuccess> getAllProvinces(@RequestParam(required = false) String facilityName,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<FacilityDTO> results = facilityService.findAll()
                .stream()
                .filter(facility -> {
                    if (facilityName != null)
                        return facility.getFacilityName().toLowerCase().contains(facilityName.trim().toLowerCase());
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(facility -> modelMapper.map(facility, FacilityDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no province"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(results, String.format(GET_SUCCESS, Facility.class.getSimpleName())));
    }

    /**
     * Find a Facility with facilityId
     *
     * @param facilityId
     * @return facility object with the request id
     * @throws EntityNotFoundException
     */
    @GetMapping("/facilities/{facilityId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer facilityId) throws EntityNotFoundException {
        Facility existedModel = facilityService.findById(facilityId);
        FacilityDTO resDTO = modelMapper.map(existedModel, FacilityDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Facility.class.getSimpleName())));
    }

    /**
     *  Update a facility obj with reqDTO and id
     *
     * @param facilityId
     * @param reqDTO
     * @return facility object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/facilities/{facilityId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer facilityId,
                                             @RequestBody @Valid FacilityDTO reqDTO) throws EntityNotFoundException {
        Facility rqModel = modelMapper.map(reqDTO, Facility.class);
        rqModel.setFacilityId(facilityId);
        FacilityDTO resDTO = modelMapper.map(facilityService.save(rqModel), FacilityDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Facility.class.getSimpleName())));
    }

    /**
     * Delete a facility object by id
     *
     * @param facilityId
     * @return
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/facilities/{facilityId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer facilityId) throws EntityNotFoundException {
        facilityService.deleteById(facilityId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }
}
