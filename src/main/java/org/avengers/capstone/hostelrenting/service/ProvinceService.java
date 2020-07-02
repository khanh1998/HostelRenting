package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.model.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    Province save(Province province);
    Province findById(Integer id);
    List<Province> findAll();
    void delete(int id);
    long getCount();
}
