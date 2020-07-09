package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Province;

import java.util.List;

public interface ProvinceService {
    Province save(Province province);
    Province findById(Integer id);
    List<Province> findAll();
    void deleteById(Integer id);

    long getCount();
}
