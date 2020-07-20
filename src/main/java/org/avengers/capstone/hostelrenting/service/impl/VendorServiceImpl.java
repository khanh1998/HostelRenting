package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor findById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(Vendor.class, "id", id.toString());
        }

        return vendorRepository.getOne(id);
    }

    @Override
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor save(Vendor vendor) {
        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent()){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "email", vendor.getEmail()));
        }else if (vendorRepository.findByPhone(vendor.getPhone()).isPresent()){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "phone", vendor.getPhone()));
        }

        return vendorRepository.save(vendor);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(Vendor.class, "id", id.toString());
        }

        vendorRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.isEmpty();
    }
}
