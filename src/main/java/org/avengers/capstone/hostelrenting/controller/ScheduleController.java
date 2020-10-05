package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HGScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOCreate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HGSchedule;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private ScheduleService scheduleService;
    private HostelGroupService hostelGroupService;
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

    /**
     * create a Schedule with a request facility obj
     *
     * @param reqDTO
     * @return reqDTO
     * @throws DuplicateKeyException
     */
    @PostMapping("/schedules")
    public ResponseEntity<?> createSchedule(@RequestBody @Valid ScheduleDTO reqDTO) throws DuplicateKeyException {
        Schedule reqModel = modelMapper.map(reqDTO, Schedule.class);
        Schedule resModel = scheduleService.save(reqModel);
        ScheduleDTO resDTO = modelMapper.map(resModel, ScheduleDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}/schedules")
    public ResponseEntity<?> getScheduleByGroupId(@PathVariable Integer groupId) {
        HostelGroup hgroupModel = hostelGroupService.findById(groupId);
        Collection<HGSchedule> HGSchedules = hgroupModel.getHgSchedules();
        Collection<ScheduleDTO> scheduleDTOs = HGSchedules.stream()
                .map(hgSchedule -> {
                    ScheduleDTO dto = modelMapper.map(hgSchedule.getSchedule(), ScheduleDTO.class);
                    dto.setStartTime(hgSchedule.getStartTime());
                    dto.setEndTime(hgSchedule.getEndTime());
                    return dto;
                })
                .collect(Collectors.toList());

        // bring timeRange put into schedule after grouping
        scheduleDTOs = scheduleDTOs
                .stream()
                .collect(Collectors.groupingBy(ScheduleDTO::getScheduleId))
                .values().stream()
                .map(scheduleDTO -> ScheduleDTO
                .builder().scheduleId(scheduleDTO.get(0).getScheduleId())
                .dayOfWeek(scheduleDTO.get(0).getDayOfWeek())
                .code(scheduleDTO.get(0).getCode())
                .startTime(scheduleDTO.get(0).getStartTime())
                .endTime(scheduleDTO.get(0).getEndTime())
                        .timeRange(scheduleDTO
                                .stream()
                                .map(dto ->{
                                    ArrayList<String> timeRange = new ArrayList<>();
                                    timeRange.add(dto.getStartTime() + " - " + dto.getEndTime());
                                    return timeRange;
                                })
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList())).build()).collect(Collectors.toList());


        HGScheduleDTO resDTO = new HGScheduleDTO();
        resDTO.setSchedules(scheduleDTOs);
        resDTO.setHGroup(modelMapper.map(hgroupModel, GroupDTOCreate.class));

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your schedule has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);

    }

    /**
     * Find a Schedule with scheduleId
     *
     * @param scheduleId
     * @return schedule object with the request id
     * @throws EntityNotFoundException
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<?> getById(@PathVariable Integer scheduleId) throws EntityNotFoundException {
        Schedule existedModel = scheduleService.findById(scheduleId);
        ScheduleDTO resDTO = modelMapper.map(existedModel, ScheduleDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Schedule.class.getSimpleName())));
    }

    /**
     * Update a schedule obj with reqDTO and id
     *
     * @param scheduleId
     * @param reqDTO
     * @return facility object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer scheduleId,
                                             @RequestBody @Valid ScheduleDTO reqDTO) throws EntityNotFoundException {
        Schedule rqModel = modelMapper.map(reqDTO, Schedule.class);
        rqModel.setScheduleId(scheduleId);
        ScheduleDTO resDTO = modelMapper.map(scheduleService.save(rqModel), ScheduleDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Schedule.class.getSimpleName())));
    }

    /**
     * Delete a schedule object by id
     *
     * @param scheduleId
     * @return
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer scheduleId) throws EntityNotFoundException {
        scheduleService.deleteById(scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }
}
