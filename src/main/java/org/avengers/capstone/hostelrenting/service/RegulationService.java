package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.GroupRegulation;
import org.avengers.capstone.hostelrenting.model.Regulation;
import org.springframework.stereotype.Service;

/**
 * @author duattt on 10/8/20
 * @created 08/10/2020 - 11:22
 * @project youthhostelapp
 */
public interface RegulationService {
    /**
     * Find facility by given id
     *
     * @param id the given id
     * @return facility model
     */
    Regulation findById(Integer id);
}
