package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface VendorService {
    void checkExist(Long id);

    Vendor update(VendorDTOFull reqDTO);
    Vendor findById(Long id);
    List<Vendor> findAll();
    Vendor save(Vendor vendor);
    void deleteById(Long id);

}
