package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.repository.ScheduleRepository;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleDTO> findAllSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(item -> {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setScheduleId(item.getScheduleId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setDayOfWeek(item.getDayOfWeek());
            List<Integer> hostelGroupOnlyIdDTOS = item.getHostelGroupSchedules().stream().map(item2 -> item2.getHostelGroup().getHostelGroupId()).collect(Collectors.toList());
            dto.setHostelGroupIds(hostelGroupOnlyIdDTOS);
            return dto;
        }).collect(Collectors.toList());
        return scheduleDTOList;
    }

//    @Override
//    public List<Schedule> findScheduleByHostelGroupId(Integer hostelGroupId) {
////        return scheduleRepository.findAllByHostelGroups_HostelGroupId(hostelGroupId);
//        return null;
//    }

    @Override
    public Schedule findScheduleById(Integer id) {
        Optional<Schedule>  schedules = scheduleRepository.findById(id);
        if (schedules.isEmpty()){
            throw new EntityNotFoundException(Schedule.class, "id", id.toString());
        }else{
            return schedules.get();
        }
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void removeSchedule(Integer scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
