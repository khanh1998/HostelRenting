package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Province;

import java.awt.print.Pageable;
import java.util.List;

public interface ProvinceService {
    Province save(Province province);
    Province findById(Integer id);
    List<Province> findAll(Integer page, Integer size);
}
