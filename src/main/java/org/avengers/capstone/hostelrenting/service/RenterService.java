package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.List;

public interface RenterService {
    void checkExist(Integer id);
    User update(UserDTOFull reqDTO);

    Renter findById(Integer id);
    List<Renter> findAll();
    Renter save(Renter renter);
    void deleteById(Integer id);

}
