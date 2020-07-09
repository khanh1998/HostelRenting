package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.repository.WardRepository;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WardServiceImpl implements WardService {

    WardRepository wardRepository;

    @Autowired
    public void setWardRepository(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    @Override
    public Ward findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Ward.class, "id", id.toString());
        }
        return wardRepository.getOne(id);
    }

    @Override
    public List<Ward> findAll() {
        return wardRepository.findAll();
    }

    @Override
    public Ward save(Ward ward) {
        if (wardRepository.getByWardName(ward.getWardName()) != null){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "ward_name", ward.getWardName()));
        }
        return wardRepository.save(ward);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(Ward.class, "id", id.toString());
        }
        wardRepository.deleteById(id);
    }

    @Override
    public Ward findByIdAndDistrictId(Integer wardId, Integer districtId) {
        Optional<Ward> ward = wardRepository.findByWardIdAndDistrict_DistrictId(wardId, districtId);
        if (ward.isEmpty()){
            throw new EntityNotFoundException(Ward.class, "district_id", districtId.toString(), "ward_id", wardId.toString());
        }
        return ward.get();
    }

    private boolean isNotFound(Integer id) {
        Optional<Ward> ward = wardRepository.findById(id);
        return ward.isEmpty();
    }
}
