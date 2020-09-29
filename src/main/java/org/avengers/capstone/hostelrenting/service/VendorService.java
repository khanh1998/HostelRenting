package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface VendorService {
    void checkExist(Long id);

    Vendor update(ResVendorDTO reqDTO);
    Vendor findById(Long id);
    List<Vendor> findAll();
    Vendor save(Vendor vendor);
    void deleteById(Long id);

}
