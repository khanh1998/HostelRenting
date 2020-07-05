package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.HostelGroupNoIdDTO;
import org.avengers.capstone.hostelrenting.dto.HostelGroupScheduleDTO;
import org.avengers.capstone.hostelrenting.dto.ScheduleNoIdDTO;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelGroupSchedule;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.util.List;

public interface HostelGroupScheduleService {
    HostelGroupSchedule findHostelGroupScheduleById(Integer hostelGroupScheduleId);
    List<HostelGroupNoIdDTO> findAllHostelGroupByScheduleId(Integer scheduleId);
    List<ScheduleNoIdDTO> findScheduleByHostelGroupId(Integer hostelGroupId);
    HostelGroupSchedule saveHostelGroupSchedule(HostelGroupSchedule hostelGroupSchedule);
    void deleteHostelGroupSchedule(HostelGroupSchedule hostelGroupSchedule);
}
