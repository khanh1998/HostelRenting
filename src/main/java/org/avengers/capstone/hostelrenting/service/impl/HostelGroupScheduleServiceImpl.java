package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.HostelGroupNoIdDTO;
import org.avengers.capstone.hostelrenting.dto.HostelGroupScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleNoIdDTO;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelGroupSchedule;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.repository.HostelGroupScheduleRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelGroupScheduleServiceImpl implements HostelGroupScheduleService {
    HostelGroupScheduleRepository hostelGroupScheduleRepository;

    @Autowired
    public void setHostelGroupScheduleRepository(HostelGroupScheduleRepository hostelGroupScheduleRepository) {
        this.hostelGroupScheduleRepository = hostelGroupScheduleRepository;
    }

    @Override
    public HostelGroupSchedule findHostelGroupScheduleById(Integer hostelGroupScheduleId) {
        return hostelGroupScheduleRepository.findById(hostelGroupScheduleId).get();
    }

    @Override
    public List<HostelGroupNoIdDTO> findAllHostelGroupByScheduleId(Integer scheduleId) {
        List<HostelGroup> hostelGroupSchedules = hostelGroupScheduleRepository.findAllHostelGroupByScheduleId(scheduleId);
        List<HostelGroupNoIdDTO> hostelGroupNoIdDTOS = hostelGroupSchedules.stream().map(item -> {
            HostelGroupNoIdDTO hostelGroupNoIdDTO = new HostelGroupNoIdDTO();
            hostelGroupNoIdDTO.setHostelGroupId(item.getHostelGroupId());
            hostelGroupNoIdDTO.setHostelGroupName(item.getHostelGroupName());
            hostelGroupNoIdDTO.setDetailedAddress(item.getDetailedAddress());
            hostelGroupNoIdDTO.setLatitude(item.getLatitude());
            return hostelGroupNoIdDTO;
        }).collect(Collectors.toList());
        return hostelGroupNoIdDTOS;
    }

    @Override
    public List<ScheduleNoIdDTO> findScheduleByHostelGroupId(Integer hostelGroupId) {
        List<Schedule> hostelGroupSchedules = hostelGroupScheduleRepository.findAllScheduleByHostelGroupId(hostelGroupId);
        List<ScheduleNoIdDTO> scheduleNoIdDTOS = hostelGroupSchedules.stream().map(item -> {
            ScheduleNoIdDTO scheduleNoIdDTO = new ScheduleNoIdDTO();
            scheduleNoIdDTO.setScheduleId(item.getScheduleId());
            scheduleNoIdDTO.setStartTime(item.getStartTime());
            scheduleNoIdDTO.setEndTime(item.getEndTime());
            scheduleNoIdDTO.setDayOfWeek(item.getDayOfWeek());
            return scheduleNoIdDTO;
        }).collect(Collectors.toList());
        return scheduleNoIdDTOS;
    }

    @Override
    public HostelGroupSchedule saveHostelGroupSchedule(HostelGroupSchedule hostelGroupSchedule) {
        return hostelGroupScheduleRepository.save(hostelGroupSchedule);
    }

    @Override
    public void deleteHostelGroupSchedule(HostelGroupSchedule hostelGroupSchedule) {
        hostelGroupScheduleRepository.delete(hostelGroupSchedule);
    }
}
