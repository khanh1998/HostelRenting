package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.model.Renter;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface RenterService {
    void checkExist(UUID id);
    Renter updateInfo(Renter exModel, RenterDTOUpdate reqDTO);
    Renter updateToken(Renter exModel, UserDTOUpdateOnlyToken onlyTokenDTO);

    Renter findById(UUID id);
    Collection<Renter> findByIds(Collection<UUID> ids);
    Renter create(Renter renter);
}
