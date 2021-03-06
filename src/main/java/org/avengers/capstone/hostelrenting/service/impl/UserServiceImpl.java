package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private RenterRepository renterRepository;
    private VendorRepository vendorRepository;

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public User login(String phone, String password) {
        Optional<Vendor> existedVendor = vendorRepository.findByPhone(phone);
        Optional<Renter> existedRenter = renterRepository.findByPhone(phone);
        if (existedRenter.isPresent()){
            if (existedRenter.get().getPassword().equals(password))
                return existedRenter.get();
        }else if(existedVendor.isPresent()){
            if (existedVendor.get().getPassword().equals(password))
                return existedVendor.get();
        }
        return null;
    }
}
