package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleNoIdDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.ResourceNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
public class ScheduleController{
    private ScheduleService scheduleService;

    private ModelMapper modelMapper;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedules")
    public ResponseEntity<ApiSuccess> getAllSchedule() {
        List<ScheduleDTO> results = scheduleService.findAllSchedule()
                .stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no schedule"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Schedule")));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> getScheduleById(@PathVariable Integer scheduleId) throws EntityNotFoundException {
        Schedule schedule = scheduleService.findScheduleById(scheduleId);
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(scheduleDTO, String.format(GET_SUCCESS, "Schedule")));
    }


    @PostMapping("/schedules")
    public ResponseEntity<ApiSuccess> createSchedule(@Valid @RequestBody ScheduleNoIdDTO scheduleNoIdDTO) throws DuplicateKeyException {
        Schedule schedule = modelMapper.map(scheduleNoIdDTO, Schedule.class);
        Schedule createdSchedule = scheduleService.saveSchedule(schedule);
        scheduleNoIdDTO = modelMapper.map(createdSchedule, ScheduleNoIdDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(scheduleNoIdDTO, String.format(CREATE_SUCCESS, "Schedule")));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> updateSchedule(@PathVariable Integer scheduleId, @RequestBody ScheduleNoIdDTO scheduleNoIdDTO) throws EntityNotFoundException {
        Schedule oldSchedule = modelMapper.map(scheduleNoIdDTO, Schedule.class);
        oldSchedule.setScheduleId(scheduleId);
        ScheduleNoIdDTO updatedSchedule = modelMapper.map(scheduleService.saveSchedule(oldSchedule), ScheduleNoIdDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedSchedule, String.format(UPDATE_SUCCESS, "Schedule")));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> deleteSchedule(@PathVariable Integer scheduleId) throws EntityNotFoundException{
        scheduleService.removeSchedule(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
