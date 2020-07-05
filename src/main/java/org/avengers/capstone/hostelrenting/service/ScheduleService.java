package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.ScheduleDTO;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<ScheduleDTO> findAllSchedule();
//    List<Schedule> findScheduleByHostelGroupId(Integer hostelGroupId);
    Schedule findScheduleById(Integer id);
    Schedule saveSchedule(Schedule schedule);
    void removeSchedule(Integer scheduleId);
}
