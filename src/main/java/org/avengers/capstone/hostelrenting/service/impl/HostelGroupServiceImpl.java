package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
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
        HostelGroup existedHostelGroup = hostelGroupRepository.findByLongitudeAndLatitude(hostelGroup.getLongitude(), hostelGroup.getLatitude());
        if (existedHostelGroup != null){
            throw new DuplicateKeyException(String.format("Hostel group location is dupplicated"));
        }
        return hostelGroupRepository.save(hostelGroup);
    }

    @Override
    public void delete(Integer hostelGroupId) {
        hostelGroupRepository.deleteById(hostelGroupId);
    }

    @Override
    public List<HostelGroup> findByDistrictId(Integer districtId) {
        return hostelGroupRepository.findByDistrict_DistrictId(districtId);
    }


    private boolean isNotFound(Integer id) {
        Optional<HostelGroup> hostelGroup = hostelGroupRepository.findById(id);
        return hostelGroup.isEmpty();
    }
}
