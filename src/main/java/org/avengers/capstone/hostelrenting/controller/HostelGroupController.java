package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
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
@RequestMapping("/api/v1")
public class HostelGroupController {
    private HostelGroupService hostelGroupService;

    private WardService wardService;

    private ProvinceService provinceService;

    private DistrictService districtService;

    private ModelMapper modelMapper;


    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<ApiSuccess> getHostelGroupByIdAndWardId(@PathVariable Integer groupId) throws EntityNotFoundException {

        HostelGroup hostelGroup = hostelGroupService.findById(groupId);
        HostelGroupDTO responseDTO = modelMapper.map(hostelGroup, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(responseDTO, String.format(GET_SUCCESS, "Hostel Group")));
    }

    @GetMapping("/groups")
    public ResponseEntity<ApiSuccess> getHostelGroupByWardId(@RequestParam(required = false) Integer wardId,
                                                                 @RequestParam(required = false) Integer groupId,
                                                                 @RequestParam(required = false) String hostelGroupName,
                                                                 @RequestParam(required = false) String detailedAddress,
                                                                 @RequestParam(required = false, defaultValue = "50") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page) throws EntityNotFoundException {
        List<HostelGroupDTO> responseHostelGroups = hostelGroupService.findAll().stream()
                .filter(hostelGroup -> {
                    if (wardId != null)
                        return hostelGroup.getWard().getWardId() == wardId;
                    return true;
                }).filter(hostelGroup -> {
                    if (groupId != null)
                        return hostelGroup.getGroupId() == groupId;
                    return true;
                }).filter(hostelGroup -> {
                    if (hostelGroupName != null)
                        return hostelGroup.getGroupName().contains(hostelGroupName);
                    return true;
                }).filter(hostelGroup -> {
                    if (detailedAddress != null)
                        return hostelGroup.getDetailedAddress().trim().toLowerCase().contains(detailedAddress.toLowerCase().trim());
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(hostelGroup -> modelMapper.map(hostelGroup, HostelGroupDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body((new ApiSuccess(responseHostelGroups, String.format(GET_SUCCESS, "Hostel group"))));
    }

    @PostMapping("/groups")
    public ResponseEntity<ApiSuccess> createHostelGroup(@Valid @RequestBody HostelGroupDTO rqHostelGroup) throws EntityNotFoundException {
        HostelGroup hostelGroupModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        hostelGroupModel.setWard(wardService.findById(rqHostelGroup.getWardId()));
        hostelGroupService.save(hostelGroupModel);
        HostelGroupDTO createdDTO = modelMapper.map(hostelGroupModel, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, "Hostel group")));
    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<ApiSuccess> updateHostelGroup(@PathVariable Integer groupId,
                                                        @Valid @RequestBody HostelGroupDTO rqHostelGroup) throws EntityNotFoundException {
        rqHostelGroup.setGroupId(groupId);
        HostelGroup rqModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        HostelGroup existedModel = hostelGroupService.findById(groupId);
        rqModel.setWard(existedModel.getWard());
        HostelGroup updatedModel = hostelGroupService.save(rqModel);
        HostelGroupDTO updatedDTO = modelMapper.map(updatedModel, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess((updatedDTO), String.format(UPDATE_SUCCESS, "Hostel group")));
    }

    @DeleteMapping("groups/{groupId}")
    public ResponseEntity<ApiSuccess> deleteHostelGroup(@PathVariable Integer groupId) throws EntityNotFoundException {
        hostelGroupService.deleteById(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
