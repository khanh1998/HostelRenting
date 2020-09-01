package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.UCategoryDTO;
import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.model.Utility;
import org.avengers.capstone.hostelrenting.repository.UCategoryRepository;
import org.avengers.capstone.hostelrenting.service.UCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UCategoryServiceImpl implements UCategoryService {
    private UCategoryRepository uCategoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setRepo(UCategoryRepository uCategoryRepository) {
        this.uCategoryRepository = uCategoryRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
