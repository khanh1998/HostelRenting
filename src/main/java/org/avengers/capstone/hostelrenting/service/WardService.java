package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Ward;

public interface WardService {

    void checkExist(Integer id);
    Ward findById(Integer id);
}
