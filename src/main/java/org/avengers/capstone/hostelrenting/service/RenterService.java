package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.Collection;
import java.util.List;

public interface RenterService {
    void checkExist(Long id);
    Renter updateInfo(Renter exModel, RenterDTOUpdate reqDTO);

    Renter findById(Long id);
    Collection<Renter> findByIds(Collection<Long> ids);
    List<Renter> findAll();
    Renter create(Renter renter);
}
