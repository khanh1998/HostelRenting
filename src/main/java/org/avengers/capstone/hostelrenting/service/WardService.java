package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Ward;

import java.util.List;

public interface WardService {
    Ward findById(Integer id);
    List<Ward> findAll();
    Ward save(Ward ward);
    void deleteById(Integer id);

    Ward findByIdAndDistrictId(Integer wardId, Integer districtId);
}
