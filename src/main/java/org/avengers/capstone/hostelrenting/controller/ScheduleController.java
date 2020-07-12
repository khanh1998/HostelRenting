package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Schedule;
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
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
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

    /**
     * create a Schedule with a request facility obj
     *
     * @param reqDTO
     * @return reqDTO
     * @throws DuplicateKeyException
     */
    @PostMapping("/schedules")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid ScheduleDTO reqDTO) throws DuplicateKeyException {
        Schedule rqModel = modelMapper.map(reqDTO, Schedule.class);
        Schedule createdModel = scheduleService.save(rqModel);
        ScheduleDTO responseDTO = modelMapper.map(createdModel, ScheduleDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Schedule.class.getSimpleName())));
    }

    /**
     * Filter and get list all of services
     *
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/schedules")
    public ResponseEntity<ApiSuccess> getAllProvinces(@RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<ScheduleDTO> results = scheduleService.findAll()
                .stream()
                .skip((page -1) * size)
                .limit(size)
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no schedule"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(results, String.format(GET_SUCCESS, Schedule.class.getSimpleName())));
    }

    /**
     * Find a Schedule with scheduleId
     *
     * @param scheduleId
     * @return schedule object with the request id
     * @throws EntityNotFoundException
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer scheduleId) throws EntityNotFoundException {
        Schedule existedModel = scheduleService.findById(scheduleId);
        ScheduleDTO resDTO = modelMapper.map(existedModel, ScheduleDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Schedule.class.getSimpleName())));
    }

    /**
     *  Update a schedule obj with reqDTO and id
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
