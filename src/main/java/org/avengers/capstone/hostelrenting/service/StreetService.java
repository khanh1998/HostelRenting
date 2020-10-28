package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.Ward;

public interface StreetService {
    void checkExist(Integer id);
    Street findById(Integer id);
    Street createIfNotExist(Street street);
}
