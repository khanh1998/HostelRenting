package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.GroupRegulation;
import org.avengers.capstone.hostelrenting.model.Regulation;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.avengers.capstone.hostelrenting.repository.RegulationRepository;
import org.avengers.capstone.hostelrenting.service.RegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author duattt on 10/8/20
 * @created 08/10/2020 - 11:24
 * @project youthhostelapp
 */
@Service
public class RegulationServiceImpl implements RegulationService {

    @Autowired
    private RegulationRepository regulationRepository;

//    @Autowired
//    public void setRegulationRepository(RegulationRepository regulationRepository) {
//        this.regulationRepository = regulationRepository;
//    }

    @Override
    public Regulation findById(Integer id) {
        if (regulationRepository.existsById(id)) {
            return regulationRepository.getOne(id);
        }else{
            throw new EntityNotFoundException(Regulation.class, "id", id.toString());
        }
    }

    private boolean isNotFound(Integer id) {
        Optional<Regulation> regulation = regulationRepository.findById(id);
        return regulation.isEmpty();
    }
}
