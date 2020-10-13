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

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

//    @Autowired
//    public void setDistrictRepository(DistrictRepository districtRepository) {
//        this.districtRepository = districtRepository;
//    }

    @Override
    public District findById(Integer id) {
//        if (isNotFound(id)) {
//            throw new EntityNotFoundException(District.class, "id", id.toString());
//        }
//
//        return districtRepository.getOne(id);
        if (districtRepository.existsById(id)) {
            return districtRepository.getOne(id);
        } else {
            throw new EntityNotFoundException(District.class, "id", id.toString());
        }
    }

    public void checkExist(Integer id) {
        Optional<District> model = districtRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(District.class, "id", id.toString());
    }

//    @Override
//    public District findById(Integer id) {
//        checkExist(id);
//
//        return districtRepository.getOne(id);
//    }

    @Override
    public List<District> findAll() {

        return districtRepository.findAll();
    }

    @Override
    public District save(District district) {
        if (districtRepository.getByDistrictName(district.getDistrictName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "district_name", district.getDistrictName()));
        }
        return districtRepository.save(district);

    }

    @Override
    public District findByIdAndProvinceId(Integer districtId, Integer provinceId) {
        Optional<District> district = districtRepository.findByDistrictIdAndProvince_ProvinceId(districtId, provinceId);
        if (district.isEmpty() || district == null){
            throw new EntityNotFoundException(District.class, "province_id", provinceId.toString(), "district_id", districtId.toString());
        }
        return district.get();
    }

}
