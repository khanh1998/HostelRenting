package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.repository.HostelGroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    private HostelGroupRepository hostelGroupRepository;

    @Autowired
    public void setHostelGroupRepository(HostelGroupRepository hostelGroupRepository) {
        this.hostelGroupRepository = hostelGroupRepository;
    }

    @Override
    public HostelGroup findById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(HostelGroup.class, "id", id.toString());
        }
        return hostelGroupRepository.getOne(id);
    }

    @Override
    public List<HostelGroup> findAll() {
        return hostelGroupRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public HostelGroup save(HostelGroup hostelGroup) {
        if (hostelGroupRepository.equals(hostelGroup)){
            throw new DuplicateKeyException(String.format("Hostel group is dupplicated"));
        }
        return hostelGroupRepository.save(hostelGroup);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelGroup.class, "id", id.toString());
        }
        hostelGroupRepository.deleteById(id);
    }

    @Override
    public List<HostelGroup> findByWardId(Integer districtId) {
        return hostelGroupRepository.findByWard_WardId(districtId);
    }


    private boolean isNotFound(Integer id) {
        Optional<HostelGroup> hostelGroup = hostelGroupRepository.findById(id);
        return hostelGroup.isEmpty();
    }
}
