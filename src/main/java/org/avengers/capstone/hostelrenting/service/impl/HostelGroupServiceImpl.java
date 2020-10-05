package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.repository.HostelGroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    private HostelGroupRepository hostelGroupRepository;
    private static final Logger logger = LoggerFactory.getLogger(HostelGroupServiceImpl.class);

    @Autowired
    public void setHostelGroupRepository(HostelGroupRepository hostelGroupRepository) {
        this.hostelGroupRepository = hostelGroupRepository;
    }


    @Override
    public void checkExist(Integer id) {
        Optional<HostelGroup> model = hostelGroupRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(HostelGroup.class, "id", id.toString());
    }

    @Override
    public HostelGroup findById(Integer id) {
        checkExist(id);

        return hostelGroupRepository.getOne(id);
    }

    @Override
    public HostelGroup create(HostelGroup reqModel) {
        //TODO: check duplicate
        // Set createdAt
        reqModel.setCreatedAt(System.currentTimeMillis());

        return hostelGroupRepository.save(reqModel);
    }

    @Override
    public HostelGroup update(HostelGroup reqModel) {
        // Set updatedAt
        reqModel.setUpdatedAt(System.currentTimeMillis());

        return hostelGroupRepository.save(reqModel);
    }

    @Override
    public void deleteById(Integer id) {
        //TODO: Implement
    }
}
