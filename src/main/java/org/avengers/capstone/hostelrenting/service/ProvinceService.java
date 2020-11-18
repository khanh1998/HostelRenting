package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Province;

import java.util.List;

public interface ProvinceService {
    void checkExist(Integer provinceId);
    Province create(Province reqModel);
    Province findById(Integer provinceId);
    List<Province> getAll();
}
