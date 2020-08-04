package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface VendorService {
    void checkExist(Integer id);

    User update(UserDTOFull reqDTO);
    Vendor findById(Integer id);
    List<Vendor> findAll();
    Vendor save(Vendor vendor);
    void deleteById(Integer id);

}
