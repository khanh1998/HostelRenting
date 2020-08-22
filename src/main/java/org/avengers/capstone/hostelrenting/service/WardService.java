package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.WardDTO;
import org.avengers.capstone.hostelrenting.model.Ward;

import java.util.List;

public interface WardService {

    void checkExist(Integer id);
    Ward findById(Integer id);
}
