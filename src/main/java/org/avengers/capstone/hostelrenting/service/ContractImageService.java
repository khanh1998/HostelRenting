package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.ContractImage;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 10:43
 * @project youthhostelapp
 */
public interface ContractImageService {
    void checkExist(Integer id);

    ContractImage findById(Integer id);

    ContractImage create(ContractImage reqModel);

    ContractImage update(ContractImage reqModel);
}
