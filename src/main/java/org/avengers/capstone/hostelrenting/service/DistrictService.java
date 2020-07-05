package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Province;

import java.util.List;

public interface DistrictService {
    District findById(Integer id);
    List<District> findAll();
    District save(District district);
    void delete(Integer id);
}
