package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.District;

import java.util.List;

public interface DistrictService {
    District findById(Integer id);
    List<District> findAll();
    District save(District district);
    void deleteById(Integer id);

    District findByIdAndProvinceId(Integer districtId, Integer provinceId);
}
