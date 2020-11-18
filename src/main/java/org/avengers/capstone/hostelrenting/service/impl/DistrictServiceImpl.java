package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Province;
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
    private DistrictRepository districtRepository;

    @Autowired
    public void setDistrictRepository(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<District> model = districtRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(District.class, "id", id.toString());
    }

    @Override
    public District findById(Integer id) {
        checkExist(id);

        return districtRepository.getOne(id);
    }

    @Override
    public List<District> findAll() {

        return districtRepository.findAll();
    }

    @Override
    public District create(District reqModel) {
        Optional<District> exModel = districtRepository.getByDistrictName(reqModel.getDistrictName());
        if (exModel.isPresent())
            throw new GenericException(District.class, "is duplicated",
                    "name", exModel.get().getDistrictName(),
                    "id", String.valueOf(exModel.get().getDistrictId()));

        return districtRepository.save(reqModel);

    }

    @Override
    public District findByIdAndProvinceId(Integer districtId, Integer provinceId) {
        Optional<District> district = districtRepository.findByDistrictIdAndProvince_ProvinceId(districtId, provinceId);
        if (district.isEmpty()){
            throw new EntityNotFoundException(District.class, "province_id", provinceId.toString(), "district_id", districtId.toString());
        }
        return district.get();
    }

}
