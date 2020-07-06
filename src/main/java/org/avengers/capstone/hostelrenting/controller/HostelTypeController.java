package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.HostelTypeDTO;
import org.avengers.capstone.hostelrenting.dto.HostelTypeListDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
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
public class HostelTypeController {
    private HostelTypeService hostelTypeService;
    private HostelGroupService hostelGroupService;
    private ModelMapper modelMapper;

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @GetMapping("/types")
    public ResponseEntity<ApiSuccess> getAllHostelTypes() {
        List<HostelType> results = hostelTypeService.findAllHostelType()
                .stream()
                .map(hostelTypes -> modelMapper.map(hostelTypes, HostelType.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no type for hostel"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Hostel type")));
    }

    @GetMapping("/types/{hostelTypeId}")
    public ResponseEntity<ApiSuccess> getHostelTypeById(@PathVariable Integer hostelTypeId) throws EntityNotFoundException {
        HostelType hostelType = hostelTypeService.findHostelTypeByHostelTypeId(hostelTypeId);
        HostelTypeListDTO hostelTypeListDTO = modelMapper.map(hostelType, HostelTypeListDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(hostelTypeListDTO, String.format(GET_SUCCESS, "Hostel type")));
    }

    //Lỗi
    @GetMapping("/types/{hostelGroupId}/type")
    public ResponseEntity<ApiSuccess> getHostelTypeByHostelGroupId(@PathVariable Integer hostelGroupId) throws EntityNotFoundException {
        List<HostelType> hostelType = (List<HostelType>) hostelTypeService.findAllHostelTypeByHostelGroupId(hostelGroupId).stream().map(item ->{
            HostelType hostelType1 = new HostelType();
            hostelType1.setHostelGroup(hostelGroupService.findHostelGroupByHostelGroupId(hostelGroupId));
            return hostelType1;
        }).collect(Collectors.toList());
        HostelTypeListDTO hostelTypeListDTO = modelMapper.map(hostelType, HostelTypeListDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(hostelTypeListDTO, String.format(GET_SUCCESS, "Hostel type")));
    }

    @PostMapping("/types")
    public ResponseEntity<ApiSuccess> createHostelType(@Valid @RequestBody HostelTypeDTO hostelTypeDTO) throws DuplicateKeyException {
        HostelType hostelType = modelMapper.map(hostelTypeDTO, HostelType.class);
        HostelType createdHostelType = hostelTypeService.saveHostelType(hostelType);
        hostelTypeDTO = modelMapper.map(createdHostelType, HostelTypeDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(hostelTypeDTO, String.format(CREATE_SUCCESS, "Hostel type")));
    }

    @PutMapping("/types/{hostelTypeId}")
    public ResponseEntity<ApiSuccess> updateHostelType(@PathVariable Integer hostelTypeId, @RequestBody HostelTypeDTO hostelTypeDTO) throws EntityNotFoundException {
        HostelType oldHostelType = modelMapper.map(hostelTypeDTO, HostelType.class);
        oldHostelType.setHostelTypeId(hostelTypeId);

        HostelTypeDTO updatedHostelType = modelMapper.map(hostelTypeService.saveHostelType(oldHostelType), HostelTypeDTO.class);
        updatedHostelType.setHostelGroupId(hostelTypeDTO.getHostelGroupId());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedHostelType, String.format(UPDATE_SUCCESS, "Hostel type")));
    }

    @DeleteMapping("/types/{hostelTypeId}")
    public ResponseEntity<ApiSuccess> deleteHostelType(@PathVariable Integer hostelTypeId) throws EntityNotFoundException{
        hostelTypeService.removeHostelType(hostelTypeService.findHostelTypeByHostelTypeId(hostelTypeId));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
