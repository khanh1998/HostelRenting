package capstone.tdhostel.controller;

import capstone.tdhostel.dto.ScheduleDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.Booking;
import capstone.tdhostel.model.Schedule;
import capstone.tdhostel.model.Vendor;
import capstone.tdhostel.service.ScheduleService;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController{
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/schedules")
    public List<ScheduleDTO> getAllSchedule() {
        return scheduleService.findAllSchedule();
    }

    @GetMapping("/schedules/{id}")
    public Schedule getScheduleById(@PathVariable int id) {
        return scheduleService.findScheduleById(id).get();
    }

    @GetMapping("/schedules/vendor/{vendorId}")
    public List<Schedule> getScheduleByVendorId(@PathVariable int vendorId) {
        return scheduleService.findScheduleByVendorId(vendorId);
    }

    @PostMapping("/schedules")
    public  ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setDayOfWeek(scheduleDTO.getDayOfWeek());
        schedule.setVendor(vendorService.findVendorByVendorId(scheduleDTO.getVendorId()));
        return  ResponseEntity.ok(scheduleService.saveSchedule(schedule));
    }

    @PutMapping("/schedules/{scheduleId}")
    public Schedule updateSchedule(@PathVariable int scheduleId, @RequestBody ScheduleDTO scheduleRequest) {
        return scheduleService.findScheduleById(scheduleId).map(schedule -> {
            schedule.setDayOfWeek(scheduleRequest.getDayOfWeek());
            schedule.setStartTime(scheduleRequest.getStartTime());
            schedule.setEndTime(scheduleRequest.getEndTime());
            schedule.setVendor(vendorService.findVendorByVendorId(scheduleRequest.getVendorId()));
            return scheduleService.saveSchedule(schedule);
        }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<?> removeSchedule(@PathVariable int scheduleId) {
        return scheduleService.findScheduleById(scheduleId).map(schedule -> {
            scheduleService.removeSchedule(schedule);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }
}
