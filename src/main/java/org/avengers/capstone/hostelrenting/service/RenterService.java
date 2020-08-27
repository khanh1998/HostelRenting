package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;

import java.util.List;

public interface RenterService {
    void checkExist(Integer id);
    Renter update(RenterDTOFull reqDTO);

    Renter findById(Integer id);
    List<Renter> findAll();
    Renter save(Renter renter);
    void deleteById(Integer id);

}
