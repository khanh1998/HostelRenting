package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelTypeDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("api/v1")
public class HostelTypeController {

    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;
    private HostelGroupService hostelGroupService;

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/hostelgroups/{hostelGroupId}/hosteltypes")
    public ResponseEntity<ApiSuccess> getHostelTypeByHostelGroupId(@PathVariable Integer hostelGroupId,
                                                                   @RequestParam(required = false) Integer hostelTypeId,
                                                                   @RequestParam(required = false) Long minPrice,
                                                                   @RequestParam(required = false) Long maxPrice,
                                                                   @RequestParam(required = false) Float minSuperficiality,
                                                                   @RequestParam(required = false) Float maxSuperficiality,
                                                                   @RequestParam(required = false) Integer minCapacity,
                                                                   @RequestParam(required = false) Integer maxCapacity,
                                                                   @RequestParam(required = false, defaultValue = "50") Integer size,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer page) throws EntityNotFoundException {
        List<HostelTypeDTO> responseHostelTypes = hostelTypeService.findByHostelGroupId(hostelGroupId).stream()
                .filter(hostelType -> {
                    if (hostelTypeId != null)
                        return hostelType.getHostelTypeId() == hostelTypeId;
                    return true;
                }).filter(hostelType -> {
                    if (minPrice != null)
                        return hostelType.getPrice() >= minPrice;
                    return true;
                }).filter(hostelType -> {
                    if (maxPrice != null)
                        return hostelType.getPrice() <= maxPrice;
                    return true;
                }).filter(hostelType -> {
                    if (minSuperficiality != null)
                        return hostelType.getSuperficiality() >= minSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (maxSuperficiality != null)
                        return hostelType.getSuperficiality() <= minSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (minCapacity != null)
                        return hostelType.getCapacity() >= minCapacity;
                    return true;
                }).filter(hostelType -> {
                    if (maxCapacity != null)
                        return hostelType.getCapacity() <= maxCapacity;
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(hostelType -> modelMapper.map(hostelType, HostelTypeDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.
                status(HttpStatus.OK).
                body((new ApiSuccess(responseHostelTypes, String.format(GET_SUCCESS, HostelType.class.getSimpleName()))));
    }

    @PostMapping("hostelgroups/{hostelGroupId}/hosteltypes")
    public ResponseEntity<ApiSuccess> create(@PathVariable Integer hostelGroupId,
                                             @Valid @RequestBody HostelTypeDTO rqHostelType) throws EntityNotFoundException {
        HostelType model = modelMapper.map(rqHostelType, HostelType.class);
        HostelGroup hostelGroup = hostelGroupService.findById(hostelGroupId);
        model.setHostelGroup(hostelGroup);
        //TODO: category and typestatus
        HostelType createdModel = hostelTypeService.save(model);
        HostelTypeDTO createdDTO = modelMapper.map(createdModel, HostelTypeDTO.class);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, HostelType.class.getSimpleName())));
    }

    @PutMapping("/hostelgroups/{hostelGroupId}/hosteltypes/{hostelTypeId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer hostelTypeId,
                                             @PathVariable Integer hostelGroupId,
                                             @Valid @RequestBody HostelTypeDTO rqHostelType)throws  EntityNotFoundException{
        // get important info
        HostelGroup hostelGroup = hostelGroupService.findById(hostelGroupId);
        Category category = hostelTypeService.findById(hostelTypeId).getCategory();

        rqHostelType.setHostelTypeId(hostelTypeId);
        HostelType rqModel = modelMapper.map(rqHostelType, HostelType.class);
        rqModel.setHostelGroup(hostelGroup);
        rqModel.setCategory(category);
        HostelType updatedModel = hostelTypeService.save(rqModel);
        HostelTypeDTO updatedDTO = modelMapper.map(updatedModel, HostelTypeDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, HostelType.class.getSimpleName())));
    }

    @DeleteMapping("hostelgroups/{hostelGroupId}/hosteltypes/{hostelTypeId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer hostelGroupId,
                                             @PathVariable Integer hostelTypeId) throws EntityNotFoundException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }

}
