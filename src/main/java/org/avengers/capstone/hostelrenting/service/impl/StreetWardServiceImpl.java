package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.StreetWard;
import org.avengers.capstone.hostelrenting.repository.StreetWardRepository;
import org.avengers.capstone.hostelrenting.service.StreetWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 9/25/20
 * @created 25/09/2020 - 15:14
 * @project youthhostelapp
 */
@Service
public class StreetWardServiceImpl implements StreetWardService {
    StreetWardRepository streetWardRepository;

    @Autowired
    public void setStreetWardRepository(StreetWardRepository streetWardRepository) {
        this.streetWardRepository = streetWardRepository;
    }


    @Override
    public void checkExist(Integer id) {
        Optional<StreetWard> model = streetWardRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Street.class, "id", id.toString());
    }

    @Override
    public StreetWard findById(Integer id) {
        checkExist(id);
        return streetWardRepository.getOne(id);
    }

    @Override
    public StreetWard findByStreetIdAndWardId(Integer streetId, Integer wardId) {
        return streetWardRepository.findByStreet_StreetIdAndWard_WardId(streetId, wardId);
    }
}
