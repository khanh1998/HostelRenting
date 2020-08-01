package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface VendorService {
    void checkExist(Integer id);

    Vendor findById(Integer id);
    List<Vendor> findAll();
    Vendor save(Vendor vendor);
    void deleteById(Integer id);

}
