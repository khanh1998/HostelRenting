package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.TypeStatusService;
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
public class TypeStatusController{
    private TypeStatusService typeStatusService;
    private ModelMapper modelMapper;

    @Autowired
    public void setTypeStatusService(TypeStatusService typeStatusService) {
        this.typeStatusService = typeStatusService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/status")
    public ResponseEntity<ApiSuccess> getAllStatus() {
        List<TypeStatus> results = typeStatusService.findAllTypeStatus()
                .stream()
                .map(typeStatus -> modelMapper.map(typeStatus, TypeStatus.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no status type"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Type status")));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<ApiSuccess> getTypeStatusById(@PathVariable Integer statusId) throws EntityNotFoundException {
        TypeStatus typeStatus = typeStatusService.findTypeStatusById(statusId);
        TypeStatusDTO typeStatusDTO = modelMapper.map(typeStatus, TypeStatusDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(typeStatusDTO, String.format(GET_SUCCESS, "Type Status")));
    }

    @PostMapping("/status")
    public ResponseEntity<ApiSuccess> createTypeStatus(@Valid @RequestBody TypeStatusDTO typeStatusDTO) throws DuplicateKeyException {

        TypeStatus typeStatus = modelMapper.map(typeStatusDTO, TypeStatus.class);
        TypeStatus createdTypeStatus = typeStatusService.save(typeStatus);
        typeStatusDTO = modelMapper.map(createdTypeStatus, TypeStatusDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(typeStatusDTO, String.format(CREATE_SUCCESS, "Type status")));
    }

    @PutMapping("/status/{statusId}")
    public ResponseEntity<ApiSuccess> updateTypeStatus(@PathVariable Integer statusId, @RequestBody TypeStatusDTO typeStatusDTO) throws EntityNotFoundException {
        TypeStatus oldTypeStatus = modelMapper.map(typeStatusDTO, TypeStatus.class);
        oldTypeStatus.setStatusId(statusId);
        TypeStatusDTO updatedTypeStatus = modelMapper.map(typeStatusService.save(oldTypeStatus), TypeStatusDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedTypeStatus, String.format(UPDATE_SUCCESS, "Type status")));
    }

    @DeleteMapping("/status/{statusId}")
    public ResponseEntity<ApiSuccess> deleteTypeStatus(@PathVariable Integer statusId) throws EntityNotFoundException{
        typeStatusService.deleteTypeStatus(statusId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
