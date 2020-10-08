package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    private GroupRepository groupRepository;
    private static final Logger logger = LoggerFactory.getLogger(HostelGroupServiceImpl.class);

    @Autowired
    public void setHostelGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public void checkExist(Integer id) {
        Optional<Group> model = groupRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Group.class, "id", id.toString());
    }

    @Override
    public Group findById(Integer id) {
        checkExist(id);

        return groupRepository.getOne(id);
    }

    @Override
    public Group create(Group reqModel) {
        //TODO: check duplicate
        // Set createdAt
        reqModel.setCreatedAt(System.currentTimeMillis());

        return groupRepository.save(reqModel);
    }

    @Override
    public Group update(Group reqModel) {
        // Set updatedAt
        reqModel.setUpdatedAt(System.currentTimeMillis());

        return groupRepository.save(reqModel);
    }

    @Override
    public void deleteById(Integer id) {
        //TODO: Implement
    }
}
