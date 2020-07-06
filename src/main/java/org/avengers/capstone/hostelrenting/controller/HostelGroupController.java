package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelGroupSchedule;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.HostelGroupScheduleService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
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
public class HostelGroupController {
    private HostelGroupService hostelGroupService;

    private ScheduleService scheduleService;

    private HostelGroupScheduleService hostelGroupScheduleService;

    private ModelMapper modelMapper;

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setHostelGroupScheduleService(HostelGroupScheduleService hostelGroupScheduleService) {
        this.hostelGroupScheduleService = hostelGroupScheduleService;
    }

    @GetMapping("/groups")
    public ResponseEntity<ApiSuccess> getAllHostelGroup() {
        List<HostelGroupDTO> results = hostelGroupService.findAllHostelGroup()
                .stream()
                .map(hostelGroup -> modelMapper.map(hostelGroup, HostelGroupDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no hostel group"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Hostel Group")));
    }

    @GetMapping("/groups/{hostelGroupId}")
    public ResponseEntity<ApiSuccess> getHostelGroupById(@PathVariable Integer hostelGroupId) throws EntityNotFoundException {
        HostelGroup hostelGroup = hostelGroupService.findHostelGroupByHostelGroupId(hostelGroupId);
        HostelGroupDTO hostelGroupDTO = modelMapper.map(hostelGroup, HostelGroupDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(hostelGroupDTO, String.format(GET_SUCCESS, "Hostel Group")));
    }

    @GetMapping("/groups/group/{hostelGroupId}")
    public ResponseEntity<ApiSuccess> getAllScheduleByHostelGroupId(@PathVariable Integer hostelGroupId) throws EntityNotFoundException {
        List<ScheduleNoIdDTO> results = hostelGroupScheduleService.findScheduleByHostelGroupId(hostelGroupId)
                .stream()
                .map(scheduleNoIdDTO -> modelMapper.map(scheduleNoIdDTO, ScheduleNoIdDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no schedule for hostel group"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Schedule")));
    }

    @GetMapping("/groups/schedule/{scheduleId}")
    public ResponseEntity<ApiSuccess> getAllHostelGroupByScheduleId(@PathVariable Integer scheduleId) throws EntityNotFoundException {
        List<HostelGroupNoIdDTO> results = hostelGroupScheduleService.findAllHostelGroupByScheduleId(scheduleId)
                .stream()
                .map(hostelGroupNoIdDTO -> modelMapper.map(hostelGroupNoIdDTO, HostelGroupNoIdDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no hostel group booking this schedule"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Schedule")));
    }

    @PostMapping("/groups/group/schedule")
    public ResponseEntity<?> createScheduleForHostelGroup(@Valid @RequestBody HostelGroupScheduleDTO hostelGroupScheduleDTO) throws DuplicateKeyException {
        HostelGroupScheduleFullDTO hostelGroupScheduleFullDTO = new HostelGroupScheduleFullDTO();
        hostelGroupScheduleFullDTO.setHostelGroup(hostelGroupService.findHostelGroupByHostelGroupId(hostelGroupScheduleDTO.getHostelGroupId()));
        hostelGroupScheduleFullDTO.setSchedule(scheduleService.findScheduleById(hostelGroupScheduleDTO.getScheduleId()));

        HostelGroupSchedule HostelGroupSchedule = modelMapper.map(hostelGroupScheduleFullDTO, HostelGroupSchedule.class);
        HostelGroupSchedule createHostelGroupSchedule = hostelGroupScheduleService.saveHostelGroupSchedule(HostelGroupSchedule);
        hostelGroupScheduleFullDTO = modelMapper.map(createHostelGroupSchedule, HostelGroupScheduleFullDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(hostelGroupScheduleFullDTO, String.format(CREATE_SUCCESS, "Schedule for hostel group")));
    }

    @PutMapping("/groups/group/{hostelGroupScheduleId}")
    public ResponseEntity<ApiSuccess> updateScheduleForHostelGroup(@PathVariable int hostelGroupScheduleId, @RequestBody HostelGroupScheduleDTO hostelGroupScheduleRequest) {
        HostelGroupScheduleFullDTO hostelGroupScheduleFullDTO = new HostelGroupScheduleFullDTO();
        hostelGroupScheduleFullDTO.setHostelGroup(hostelGroupService.findHostelGroupByHostelGroupId(hostelGroupScheduleRequest.getHostelGroupId()));
        hostelGroupScheduleFullDTO.setSchedule(scheduleService.findScheduleById(hostelGroupScheduleRequest.getScheduleId()));

        HostelGroupSchedule oldHostelGroupSchedule = modelMapper.map(hostelGroupScheduleFullDTO, HostelGroupSchedule.class);
        oldHostelGroupSchedule.setHostelGroupScheduleId(hostelGroupScheduleId);
        HostelGroupScheduleFullDTO updatedHostelGroupSchedule = modelMapper.map(hostelGroupScheduleService.saveHostelGroupSchedule(oldHostelGroupSchedule), HostelGroupScheduleFullDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedHostelGroupSchedule, String.format(UPDATE_SUCCESS, "Schedule for hostel group")));
    }

//    @DeleteMapping("/groups/group/{hostelGroupScheduleId}")
//    public ResponseEntity<?> removeScheduleInHostelGroup(@PathVariable int hostelGroupScheduleId) {
//        hostelGroupScheduleService.deleteHostelGroupSchedule(hostelGroupScheduleService.findHostelGroupScheduleById(hostelGroupScheduleId));
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
//    }

    @PostMapping("/groups")
    public ResponseEntity<?> createHostelGroup(@Valid @RequestBody HostelGroupNoIdDTO hostelGroupDTO) throws DuplicateKeyException {
        HostelGroup hostelGroup = modelMapper.map(hostelGroupDTO, HostelGroup.class);
        HostelGroup createHostelGroup = hostelGroupService.saveHostelGroup(hostelGroup);
        hostelGroupDTO = modelMapper.map(createHostelGroup, HostelGroupNoIdDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(hostelGroupDTO, String.format(CREATE_SUCCESS, "Hostel Group")));
    }

    @PutMapping("/groups/{hostelGroupId}")
    public ResponseEntity<ApiSuccess> updateHostelGroup(@PathVariable int hostelGroupId, @RequestBody HostelGroupNoIdDTO hostelGroupRequest) {
        HostelGroup oldHostelGroup = modelMapper.map(hostelGroupRequest, HostelGroup.class);
        oldHostelGroup.setHostelGroupId(hostelGroupId);
        HostelGroupNoIdDTO updatedHostelGroup = modelMapper.map(hostelGroupService.saveHostelGroup(oldHostelGroup), HostelGroupNoIdDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedHostelGroup, String.format(UPDATE_SUCCESS, "Hostel group")));
    }

    @DeleteMapping("/groups/{hostelGroupId}")
    public ResponseEntity<?> removeHostelGroup(@PathVariable int hostelGroupId) {
        hostelGroupService.removeHostelGroup(hostelGroupId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
