package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void checkExist(Long id) {
        Optional<Vendor> model = vendorRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Vendor.class, "id", id.toString());
    }

    @Override
    public Vendor update(VendorDTOFull reqDTO) {
        checkExist(reqDTO.getUserId());

        //Update firebase Token
        User exModel = vendorRepository.getOne(reqDTO.getUserId());
        if (exModel.getFirebaseToken() == null
                || !exModel.getFirebaseToken().equals(reqDTO.getFirebaseToken())) {
            exModel.setFirebaseToken(reqDTO.getFirebaseToken());

            return vendorRepository.save(modelMapper.map(exModel, Vendor.class));
        }

        return modelMapper.map(exModel, Vendor.class);
    }

    @Override
    public Vendor findById(Long id) {
        checkExist(id);
        return vendorRepository.getOne(id);

    }

    @Override
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor save(Vendor vendor) {
        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent()) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "email", vendor.getEmail()));
        } else if (vendorRepository.findByPhone(vendor.getPhone()).isPresent()) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "phone", vendor.getPhone()));
        }

        return vendorRepository.save(vendor);
    }

    @Override
    public void deleteById(Long id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Vendor.class, "id", id.toString());
        }

        vendorRepository.deleteById(id);
    }

    private boolean isNotFound(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.isEmpty();
    }
}
