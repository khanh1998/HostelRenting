package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupScheduleDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.GET_SUCCESS;
import static org.avengers.capstone.hostelrenting.Constant.Message.UPDATE_SUCCESS;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private ScheduleService scheduleService;
    private GroupService groupService;
    private ModelMapper modelMapper;

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

    /**
     * create a Schedule with a request facility obj
     *
     * @param reqDTO
     * @return reqDTO
     * @throws DuplicateKeyException
     */
    @PostMapping("/schedules")
    public ResponseEntity<?> createSchedule(@RequestBody @Valid GroupScheduleDTOResponse reqDTO) throws DuplicateKeyException {
        Schedule reqModel = modelMapper.map(reqDTO, Schedule.class);
        Schedule resModel = scheduleService.save(reqModel);
        GroupScheduleDTOResponse resDTO = modelMapper.map(resModel, GroupScheduleDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}/schedules")
    public ResponseEntity<?> getScheduleByGroupId(@PathVariable Integer groupId) {
        Group group = groupService.findById(groupId);
        GroupDTOResponse resDTO = modelMapper.map(group, GroupDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
