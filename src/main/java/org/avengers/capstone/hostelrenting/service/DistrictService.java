package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.District;

import java.util.List;

public interface DistrictService {
    void checkExist(Integer id);
    District findById(Integer id);
    List<District> findAll();
    District save(District district);

    District findByIdAndProvinceId(Integer districtId, Integer provinceId);
}
