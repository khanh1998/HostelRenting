package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.School;
import org.avengers.capstone.hostelrenting.repository.SchoolRepository;
import org.avengers.capstone.hostelrenting.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;

    @Autowired
    public void setSchoolRepository(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<School> getAll() {
        return schoolRepository.findAll();
    }

    @Override
    public School findById(Integer id) {
        return schoolRepository.getOne(id);
    }
}
