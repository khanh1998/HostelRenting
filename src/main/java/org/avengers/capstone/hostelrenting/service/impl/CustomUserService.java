package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.RenterRepository;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {
    private VendorRepository vendorRepository;
    private RenterRepository renterRepository;

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Vendor> existedVendor = vendorRepository.findByPhone(phone);
        if (existedVendor.isPresent()) {
            return new User(phone, existedVendor.get().getPassword(), new ArrayList<>());
        }

        Optional<Renter> existedRenter = renterRepository.findByPhone(phone);
        if (existedRenter.isPresent()) {
            return new User(phone, existedRenter.get().getPassword(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found with phone: " + phone);

    }

    public org.avengers.capstone.hostelrenting.model.User findByPhone(String phone) {
        Optional<Vendor> existedVendor = vendorRepository.findByPhone(phone);
        Optional<Renter> existedRenter = renterRepository.findByPhone(phone);
        if (existedRenter.isPresent()) {
            existedRenter.get().setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.RENTER);
            return existedRenter.get();
        } else if (existedVendor.isPresent()) {
            existedVendor.get().setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.VENDOR);
            return existedVendor.get();
        }
        return null;
    }
}
