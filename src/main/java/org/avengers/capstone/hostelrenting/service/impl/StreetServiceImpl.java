package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.repository.StreetRepository;
import org.avengers.capstone.hostelrenting.service.StreetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StreetServiceImpl implements StreetService {
    @Autowired
    private StreetRepository streetRepository;
    private ModelMapper modelMapper;

//    @Autowired
//    public void setRepository(StreetRepository streetRepository) {
//        this.streetRepository = streetRepository;
//    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Street> model = streetRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Street.class, "id", id.toString());
    }

    @Override
    public Street findById(Integer id) {
//        checkExist(id);
//        return streetRepository.getOne(id);
        if (streetRepository.existsById(id))
            return streetRepository.getOne(id);
        else
            throw new EntityNotFoundException(Street.class, "id", id.toString());
    }

}
