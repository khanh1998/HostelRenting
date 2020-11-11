package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.VendorRepository;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Vendor findById(Long id) {
        checkExist(id);
        return vendorRepository.getOne(id);

    }

    @Override
    public Vendor create(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

}
