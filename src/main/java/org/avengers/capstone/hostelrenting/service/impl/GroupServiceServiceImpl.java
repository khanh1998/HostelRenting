package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.repository.GroupServiceRepository;
import org.avengers.capstone.hostelrenting.service.GroupServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 10/16/20
 * @created 16/10/2020 - 15:08
 * @project youthhostelapp
 */
@Service
public class GroupServiceServiceImpl implements GroupServiceService {
    private GroupServiceRepository groupServiceRepository;

    @Autowired
    public void setGroupServiceRepository(GroupServiceRepository groupServiceRepository) {
        this.groupServiceRepository = groupServiceRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<GroupService> model = groupServiceRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(GroupService.class, "id", id.toString());
    }

    @Override
    public GroupService findById(Integer id) {
        checkExist(id);
        return groupServiceRepository.getOne(id);
    }
}
