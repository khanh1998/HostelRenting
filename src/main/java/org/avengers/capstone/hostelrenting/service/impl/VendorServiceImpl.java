package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.UserService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void checkExist(UUID id) {
        Optional<Vendor> model = vendorRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Vendor.class, "id", id.toString());
    }

    @Override
    public Vendor update(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateInfo(Vendor exModel, VendorDTOUpdate reqDTO) {
        modelMapper.map(reqDTO, exModel);

        return vendorRepository.save(exModel);
    }

    @Override
    public Vendor updateToken(Vendor exModel, UserDTOUpdateOnlyToken onlyTokenDTO) {
        modelMapper.map(onlyTokenDTO, exModel);
        return vendorRepository.save(exModel);
    }

    @Override
    public Vendor findById(UUID id) {
        checkExist(id);
        return vendorRepository.getOne(id);

    }

    @Override
    public Vendor create(Vendor vendor) {
        // check duplicate phone
        userService.checkDuplicatePhone(vendor.getPhone());
        return vendorRepository.save(vendor);
    }

    @Override
    public Collection<Vendor> getAllVendors(int page, int size, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - Constant.ONE, size, sort);
        return vendorRepository.findAll(pageable).toList();
    }

}
