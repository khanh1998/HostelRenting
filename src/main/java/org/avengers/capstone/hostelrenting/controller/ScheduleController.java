package org.avengers.capstone.hostelrenting.controller;

import com.sun.mail.iap.Response;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseV2;
import org.avengers.capstone.hostelrenting.dto.schedule.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.service.GroupScheduleService;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private ScheduleService scheduleService;
    private GroupService groupService;
    private ModelMapper modelMapper;
    private GroupScheduleService groupScheduleService;

    @Autowired
    public void setGroupScheduleService(GroupScheduleService groupScheduleService) {
        this.groupScheduleService = groupScheduleService;
    }

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("/groups/{groupId}/schedules")
    public ResponseEntity<?> createGroupSchedule(@RequestBody @Valid Collection<GroupScheduleDTOCreate> reqDTOs,
                                            @PathVariable Integer groupId) throws DuplicateKeyException {
        Group exGroup = groupService.findById(groupId);

        Collection<GroupSchedule> schedules = reqDTOs
                .stream()
                .map(dto -> {
                    List<GroupSchedule> models = new ArrayList<>();
                    for (String s : dto.getTimeRange()) {
                        GroupSchedule model = GroupSchedule.builder()
                                .group(exGroup)
                                .schedule(scheduleService.findById(dto.getScheduleId()))
                                .startTime(s.substring(0, s.indexOf("-") - 1))
                                .endTime(s.substring(s.indexOf("-") + 2))
                                .build();
                        models.add(model);
                    }
                    return models;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        exGroup.setGroupSchedules(schedules);
        Group resGroup = groupService.save(exGroup);
        Collection<GroupScheduleDTOResponse> resDTOs = modelMapper.map(resGroup, GroupDTOResponse.class).getGroupSchedules();
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your schedule has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @DeleteMapping("/schedules/{groupScheduleId}")
    public ResponseEntity<?> deleteGroupScheduleByGroupId(@PathVariable Integer groupScheduleId){
        groupScheduleService.delete(groupScheduleId);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(null, "Your schedule has been deleted successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}/schedules")
    public ResponseEntity<?> getGroupScheduleByGroupId(@PathVariable Integer groupId) {
        Group group = groupService.findById(groupId);
        Collection<GroupScheduleDTOResponse> resDTO = modelMapper.map(group, GroupDTOResponse.class).getGroupSchedules();
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    @GetMapping("/schedules")
    public ResponseEntity<?> getAllGroupSchedules() {
        Collection<ScheduleDTO> resDTOs = scheduleService.findAll()
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your schedule has been created successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}/schedulesv2")
    public ResponseEntity<?> getScheduleByGroupIdV2(@PathVariable Integer groupId){
        Group group = groupService.findById(groupId);
        Collection<GroupScheduleDTOResponseV2>  resDTO =  modelMapper.map(group, GroupDTOResponseV2.class).getGroupSchedules();
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been retrieved successfully!");
        return ResponseEntity.ok(apiSuccess);
    }
}
