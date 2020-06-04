package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {
    @Autowired
    private RenterRepository renterRepository;
    public Renter createRenter(String username, String name) {
        Renter r = new Renter();
        r.setName(name);
        r.setUsername(username);
        return renterRepository.save(r);
    }

    public List<Renter> getAll() {
        return renterRepository.findAll();
    }
}
