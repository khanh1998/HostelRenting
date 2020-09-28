package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.StreetWard;

/**
 * @author duattt on 9/25/20
 * @created 25/09/2020 - 15:13
 * @project youthhostelapp
 */
public interface StreetWardService {
    void checkExist(Integer id);
    StreetWard findById(Integer id);
    StreetWard findByStreetIdAndWardId(Integer streetId, Integer wardId);
}
