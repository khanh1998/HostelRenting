package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.repository.GroupScheduleRepository;
import org.avengers.capstone.hostelrenting.service.GroupScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 22/12/2020
 * @created 22/12/2020 - 15:23
 * @project youthhostelapp
 */
@Service
public class GroupScheduleServiceImpl implements GroupScheduleService {
    private GroupScheduleRepository groupScheduleRepository;

    @Autowired
    public void setGroupScheduleRepository(GroupScheduleRepository groupScheduleRepository) {
        this.groupScheduleRepository = groupScheduleRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<GroupSchedule> model = groupScheduleRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(GroupSchedule.class, "id", id.toString());
    }

    @Override
    public GroupSchedule findById(Integer id) {
        checkExist(id);
        return groupScheduleRepository.getOne(id);
    }

    @Override
    public void delete(Integer id) {
        checkExist(id);

        groupScheduleRepository.deleteById(id);
    }
}
