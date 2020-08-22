package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.WardDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.repository.WardRepository;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WardServiceImpl implements WardService {

    private WardRepository wardRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setWardRepository(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Ward> model = wardRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Ward.class, "id", id.toString());
    }

    @Override
    public Ward findById(Integer id) {
        checkExist(id);
        return wardRepository.getOne(id);
    }
}
