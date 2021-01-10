package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.admin.AdminDTORequest;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Admin;
import org.avengers.capstone.hostelrenting.model.Manager;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.AdminRepository;
import org.avengers.capstone.hostelrenting.repository.ManagerRepository;
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
    private AdminRepository adminRepository;
    private ManagerRepository managerRepository;

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setRenterRepository(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
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

        Optional<Admin> admin = adminRepository.findByPhone(phone);
        if (admin.isPresent()) {
            return new User(phone, admin.get().getPassword(), new ArrayList<>());
        }

        Optional<Manager> manager = managerRepository.findByManagerPhone(phone);
        if (manager.isPresent()) {
            return new User(phone, manager.get().getPassword(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found with phone: " + phone);

    }

//    public AdminDTORequest loadAdminByUsername(String phone) throws UsernameNotFoundException {
//        Optional<Admin> existedAdmin = adminRepository.findByPhone(phone);
//        if (existedAdmin.isPresent()) {
//            return new AdminDTORequest(phone, existedAdmin.get().getPassword());
//        }
//
//        throw new UsernameNotFoundException("Admin not found with phone: " + phone);
//
//    }

    public org.avengers.capstone.hostelrenting.model.User findByPhone(String phone) {
        Optional<Vendor> existedVendor = vendorRepository.findByPhone(phone);
        Optional<Renter> existedRenter = renterRepository.findByPhone(phone);
        Optional<Admin> existedAdmin = adminRepository.findByPhone(phone);
        Optional<Manager> existedManager = managerRepository.findByManagerPhone(phone);
        if (existedRenter.isPresent()) {
            existedRenter.get().setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.RENTER);
            return existedRenter.get();
        } else if (existedVendor.isPresent()) {
            existedVendor.get().setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.VENDOR);
            return existedVendor.get();
        } else if (existedAdmin.isPresent()) {
            org.avengers.capstone.hostelrenting.model.User user =
                    new org.avengers.capstone.hostelrenting.model.User();
            user.setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.ADMIN);
            user.setUserId(existedAdmin.get().getUserId());
            user.setPhone(existedAdmin.get().getPhone());
            user.setPassword(existedAdmin.get().getPassword());
            return user;
        } else if (existedManager.isPresent()) {
            org.avengers.capstone.hostelrenting.model.User user =
                    new org.avengers.capstone.hostelrenting.model.User();
            user.setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.MANAGER);
            user.setUserId(existedManager.get().getManagerId());
            user.setPhone(existedManager.get().getManagerPhone());
            user.setPassword(existedManager.get().getPassword());
            return user;
        }
        throw new EntityNotFoundException(User.class, "phone", phone);
    }

//    public Admin findByPhoneAdmin(String phone){
//        Optional<Admin> existedAdmin = adminRepository.findByPhone(phone);
//        if (existedAdmin.isPresent()) {
//            existedAdmin.get().setRole(org.avengers.capstone.hostelrenting.model.User.ROLE.ADMIN);
//            return existedAdmin.get();
//        }
//        return null;
//    }
}
