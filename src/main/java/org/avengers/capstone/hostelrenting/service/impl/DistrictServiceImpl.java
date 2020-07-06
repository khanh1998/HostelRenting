package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.repository.DistrictRepository;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {
    DistrictRepository districtRepository;

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public District findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(District.class, "id", id.toString());
        }
        return districtRepository.getOne(id);
    }

    @Override
    public List<District> findAll() {
        return districtRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public District save(District district) {
        if (districtRepository.getByDistrictName(district.getDistrictName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "district_name", district.getDistrictName()));
        }
        return districtRepository.save(district);

    }

    @Override
    public void delete(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(District.class, "id", id.toString());
        }
        districtRepository.deleteById(id);

    }

    private boolean isNotFound(Integer id) {
        Optional<District> district = districtRepository.findById(id);
        return district.isEmpty();
    }

}
