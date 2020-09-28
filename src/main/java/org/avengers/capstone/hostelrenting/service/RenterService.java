package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;

import java.util.List;

public interface RenterService {
    void checkExist(Long id);
    Renter update(RenterDTOFull reqDTO);

    Renter findById(Long id);
    List<Renter> findAll();
    Renter save(Renter renter);
    void deleteById(Long id);
}
