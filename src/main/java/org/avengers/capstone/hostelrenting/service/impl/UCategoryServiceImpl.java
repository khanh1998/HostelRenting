package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.UCategoryDTO;
import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.repository.UCategoryRepo;
import org.avengers.capstone.hostelrenting.service.UCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UCategoryServiceImpl implements UCategoryService {
    private UCategoryRepo repo;
    private ModelMapper modelMapper;

    @Autowired
    public void setRepo(UCategoryRepo repo) {
        this.repo = repo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UCategoryDTO> getAll() {
        List<UCategoryDTO> res = repo.findAll()
                .stream()
                .map(uCategory -> modelMapper.map(uCategory, UCategoryDTO.class))
                .collect(Collectors.toList());
        return res;
    }
}
