package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.List;

public interface RenterService {
    void checkExist(Long id);
    Renter update(ResRenterDTO reqDTO);

    Renter findById(Long id);
    List<Renter> findAll();
    Renter save(Renter renter);
    void deleteById(Long id);
}
