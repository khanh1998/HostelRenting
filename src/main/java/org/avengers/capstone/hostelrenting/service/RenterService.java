package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.List;
import java.util.Optional;

public interface RenterService {
    List<Renter> findAllRenter();
    Optional<Renter> findRenterById(Integer id);
    Renter findRenterByRenterId(Integer id);
    Renter saveRenter(Renter renter);
    void removeRenter(Renter renter);
}
