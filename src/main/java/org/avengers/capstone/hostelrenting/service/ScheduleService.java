package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule save(Schedule schedule);
    Schedule findById(Integer id);
    List<Schedule> findAll();
    void deleteById(Integer id);

    long getCount();
}
