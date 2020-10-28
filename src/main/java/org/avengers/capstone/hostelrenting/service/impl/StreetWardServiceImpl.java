package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.StreetWard;
import org.avengers.capstone.hostelrenting.repository.StreetWardRepository;
import org.avengers.capstone.hostelrenting.service.StreetService;
import org.avengers.capstone.hostelrenting.service.StreetWardService;
import org.avengers.capstone.hostelrenting.service.WardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private StreetService streetService;
    private WardService wardService;
    private static final Logger logger = LoggerFactory.getLogger(StreetWardServiceImpl.class);
    @Autowired
    public void setWardService(WardService wardService) {
        this.wardService = wardService;
    }

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

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
        Optional<StreetWard> streetWard = streetWardRepository.findByStreet_StreetIdAndWard_WardId(streetId, wardId);
        if (streetWard.isEmpty()) {
            StreetWard newStreetWard = streetWardRepository.save(StreetWard.builder()
                    .street(streetService.findById(streetId))
                    .ward(wardService.findById(wardId))
                    .build());
            logger.info(String.format("Created streetWard: {id=%s} {streetId=%s} {streetName=%s} {wardId=%s} {wardName=%s}",
                    newStreetWard.getStreetWardId(),
                    newStreetWard.getStreet().getStreetId(),
                    newStreetWard.getStreet().getStreetName(),
                    newStreetWard.getWard().getWardId(),
                    newStreetWard.getWard().getWardName()));
            return newStreetWard;
        } else return streetWard.get();
    }
}
