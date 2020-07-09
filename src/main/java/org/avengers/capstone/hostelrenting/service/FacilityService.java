package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.Province;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FacilityService {
    Facility save(Facility facility);
    Facility findById(Integer id);
    List<Facility> findAll();
    void deleteById(Integer id);

    long getCount();
}
