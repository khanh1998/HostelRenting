package org.avengers.capstone.hostelrenting.controller;

import org.apache.catalina.Host;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.avengers.capstone.hostelrenting.service.StreetService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class HostelGroupController {
    private HostelGroupService hostelGroupService;

    private StreetService streetService;

    private ScheduleService scheduleService;

    private VendorService vendorService;

    private ModelMapper modelMapper;

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    @PostMapping("/groups")
    public ResponseEntity<?> createHostelGroup(@Valid @RequestBody HostelGroupDTO reqDTO) throws EntityNotFoundException {
        HostelGroup hostelGroupModel = modelMapper.map(reqDTO, HostelGroup.class);
        hostelGroupService.create(hostelGroupModel);
        HostelGroupDTO resDTO = modelMapper.map(hostelGroupModel, HostelGroupDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<?> updateHostelGroup(@PathVariable Integer groupId,
                                                        @Valid @RequestBody HostelGroupDTO rqHostelGroup) throws EntityNotFoundException {
        rqHostelGroup.setGroupId(groupId);
        HostelGroup existedModel = hostelGroupService.findById(groupId);
        HostelGroup rqModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        Vendor vendor = existedModel.getVendor();

//        rqModel.setStreet(existedModel.getStreet());
        rqModel.setVendor(vendor);
        HostelGroup resModel = hostelGroupService.update(rqModel);
        HostelGroupDTO resDTO = modelMapper.map(resModel, HostelGroupDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been updated successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<?> getHostelGroupById(@PathVariable Integer groupId){
        HostelGroup resModel = hostelGroupService.findById(groupId);
        HostelGroupDTO resDTO = modelMapper.map(resModel, HostelGroupDTO.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/groups")
    public ResponseEntity<?> getGroupsByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        List<HostelGroupDTO> resDTOs = existedModel.getHostelGroups()
                .stream()
                .map(hostelGroup -> modelMapper.map(hostelGroup, HostelGroupDTO.class))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
