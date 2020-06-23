package capstone.tdhostel.service.impl;

import capstone.tdhostel.dto.ScheduleDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.Schedule;
import capstone.tdhostel.repository.ScheduleRepository;
import capstone.tdhostel.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleDTO> findAllSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(item -> {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setScheduleId(item.getScheduleId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setDayOfWeek(item.getDayOfWeek());
            dto.setVendorId(item.getVendor().getVendorId());
            return dto;
        }).collect(Collectors.toList());
        return scheduleDTOList;
    }

    @Override
    public List<Schedule> findScheduleByVendorId(Integer vendorId) {
        return scheduleRepository.findAllScheduleByVendorId(vendorId);
    }

    @Override
    public Optional<Schedule> findScheduleById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void removeSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }
}
