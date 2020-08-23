package org.avengers.capstone.hostelrenting.controller;

import io.swagger.models.Response;
import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
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
                                                             @RequestParam(required = false) Integer streetId,
                                                             @RequestParam(required = false, defaultValue = "50") Integer size,
                                                             @RequestParam(required = false, defaultValue = "1") Integer page) throws EntityNotFoundException {
        List<HostelGroupDTO> responseHostelGroups = hostelGroupService.findAll().stream()

                .filter(hostelGroup -> {
                    if (streetId != null)
                        return hostelGroup.getStreet().getStreetId() == streetId;
                    return true;
                }).filter(hostelGroup -> {
                    if (groupId != null)
                        return hostelGroup.getGroupId() == groupId;
                    return true;
                }).filter(hostelGroup -> {
                    if (hostelGroupName != null)
                        return hostelGroup.getGroupName().contains(hostelGroupName);
                    return true;
                }).skip((page - 1) * size)
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
        hostelGroupModel.setStreet(streetService.findById(rqHostelGroup.getStreet().getStreetId()));
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

        rqModel.setStreet(existedModel.getStreet());
        rqModel.setVendor(vendor);
        HostelGroup updatedModel = hostelGroupService.save(rqModel);
        HostelGroupDTO updatedDTO = modelMapper.map(updatedModel, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, "Hostel group")));
    }

    @GetMapping("/vendors/{vendorId}/groups")
    public ResponseEntity<ApiSuccess> getGroupsByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        List<HostelGroupDTO> groups = existedModel.getHostelGroups()
                .stream()
                .map(hostelGroup -> modelMapper.map(hostelGroup, HostelGroupDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(groups, String.format(UPDATE_SUCCESS, "Hostel group")));
    }
}
