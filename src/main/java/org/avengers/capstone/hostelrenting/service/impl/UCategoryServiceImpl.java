package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.repository.UCategoryRepository;
import org.avengers.capstone.hostelrenting.service.UCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UCategoryServiceImpl implements UCategoryService {
    private UCategoryRepository uCategoryRepository;

    @Autowired
    public void setRepo(UCategoryRepository uCategoryRepository) {
        this.uCategoryRepository = uCategoryRepository;
    }

    @Override
    public Collection<UCategory> getAllUCategories() {
        return uCategoryRepository.findAll();
    }
}
