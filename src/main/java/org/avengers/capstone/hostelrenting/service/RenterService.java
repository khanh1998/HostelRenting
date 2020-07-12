package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.List;

public interface RenterService {
    Renter findById(Integer id);
    List<Renter> findAll();
    Renter save(Renter renter);
    void deleteById(Integer id);
}
