package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface VendorService {
    Vendor findById(Integer id);
    List<Vendor> findAll();
    Vendor save(Vendor vendor);
    void deleteById(Integer id);
    Vendor checkLogin(String phone, String password);
}
