package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class HostelGroupController {
    private HostelGroupService hostelGroupService;

    private WardService wardService;

    private ScheduleService scheduleService;

    private ServiceService serviceService;

    private ModelMapper modelMapper;


    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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

    @PostMapping("groups/{groupId}/schedules")
    public ResponseEntity<ApiSuccess> addFacility(@PathVariable Integer groupId,
                                                  @Valid @RequestBody List<ScheduleDTO> schedules) {
        HostelGroup typeModel = hostelGroupService.findById(groupId);
        Set<Schedule> matchedSchedules = schedules
                .stream()
                .filter(s -> {
                    Schedule existedSchedule = scheduleService.findById(s.getScheduleId());
                    s.setStartTime(existedSchedule.getStartTime());
                    s.setEndTime(existedSchedule.getEndTime());
                    s.setDayOfWeek(existedSchedule.getDayOfWeek());
                    if (existedSchedule != null) {
                        return true;
                    }
                    return false;
                }).map(f -> modelMapper.map(f, Schedule.class))
                .collect(Collectors.toSet());


        typeModel.setSchedules(matchedSchedules);
        hostelGroupService.save(typeModel);
        HostelGroupDTO resDTO = modelMapper.map(typeModel, HostelGroupDTO.class);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, HostelGroup.class.getSimpleName())));
    }


    @PostMapping("/groups")
    public ResponseEntity<ApiSuccess> createHostelGroup(@Valid @RequestBody HostelGroupDTO rqHostelGroup) throws EntityNotFoundException {
        HostelGroup hostelGroupModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        hostelGroupModel.setWard(wardService.findById(rqHostelGroup.getWardId()));
        hostelGroupService.save(hostelGroupModel);
        HostelGroupDTO createdDTO = modelMapper.map(hostelGroupModel, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, "Hostel group")));
    }

//    @PostMapping("/groups/{groupId}/services")
//    public ResponseEntity<ApiSuccess> addService(@PathVariable Integer groupId,
//                                                 @Valid @RequestBody List<ServiceDTO> services){
//        HostelGroup groupModel = hostelGroupService.findById(groupId);
//        Set<Service> matchedServices = services
//                .stream()
//                .filter(s ->{
//                    Service existedService = serviceService.findById(s.getServiceId());
//                    s.setServiceName(existedService.getServiceName());
//                    s.setServicePrice(existedService.getServicePrice());
//                    s.setPriceUnit(existedService.getPriceUnit());
//                    s.setUserUnit(existedService.getUserUnit());
//                    if (existedService != null)
//                        return true;
//                    return false;
//                })
//                .map(s -> modelMapper.map(s, Service.class))
//                .collect(Collectors.toSet());
//
////        groupModel.setServices(matchedServices);
//        hostelGroupService.save(groupModel);
//        HostelGroupDTO resDTO = modelMapper.map(groupModel, HostelGroupDTO.class);
//
//        return ResponseEntity.
//                status(HttpStatus.CREATED).
//                body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, HostelGroup.class.getSimpleName())));
//    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<ApiSuccess> updateHostelGroup(@PathVariable Integer groupId,
                                                        @Valid @RequestBody HostelGroupDTO rqHostelGroup) throws EntityNotFoundException {
        rqHostelGroup.setGroupId(groupId);
        HostelGroup existedModel = hostelGroupService.findById(groupId);
        HostelGroup rqModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        Vendor vendor = existedModel.getVendor();

        rqModel.setWard(existedModel.getWard());
        rqModel.setVendor(vendor);
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
