package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.repository.ScheduleRepository;
import org.avengers.capstone.hostelrenting.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        if (scheduleRepository.equals(schedule)) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "Schedule obj", "Schedule obj"));
        }
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Schedule.class, "id", id.toString());
        }
        return scheduleRepository.getOne(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Schedule.class, "id", id.toString());
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public long getCount() {
        return scheduleRepository.count();
    }

    private boolean isNotFound(Integer id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.isEmpty();
    }
}
