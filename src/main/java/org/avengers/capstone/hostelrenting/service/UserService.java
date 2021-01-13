package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;

public interface UserService {
    void checkDuplicatePhone(String phone);
}
