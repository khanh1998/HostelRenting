package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.FacilityRepository;
import org.avengers.capstone.hostelrenting.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {
    private FacilityRepository facilityRepository;

    @Autowired
    public void setFacilityRepository(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public Facility save(Facility facility) {
        if (facilityRepository.getByFacilityName(facility.getFacilityName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "facility_name", facility.getFacilityName()));
        }
        return facilityRepository.save(facility);
    }

    @Override
    public Facility findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Province.class, "id", id.toString());
        }
        return facilityRepository.getOne(id);
    }

    @Override
    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Facility.class, "id", id.toString());
        }
        facilityRepository.deleteById(id);
    }

    @Override
    public long getCount() {
        return facilityRepository.count();
    }
    private boolean isNotFound(Integer id) {
        Optional<Facility> facility = facilityRepository.findById(id);
        return facility.isEmpty();
    }
}
