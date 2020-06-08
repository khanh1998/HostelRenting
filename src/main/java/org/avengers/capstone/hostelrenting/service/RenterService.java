package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.List;

public interface RenterService {
    public Renter createRenter(RenterDTO dto);
    public List<Renter> getAll();
}
