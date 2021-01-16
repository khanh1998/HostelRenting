package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;

import java.util.Collection;
import java.util.UUID;

public interface VendorService {
    void checkExist(UUID id);
    Vendor update(Vendor vendor);
    Vendor updateInfo(Vendor exModel, VendorDTOUpdate reqDTO);
    Vendor updateToken(Vendor exModel, UserDTOUpdateOnlyToken reqDTO);
    Vendor findById(UUID id);
    Vendor create(Vendor vendor);
    Collection<Vendor> getAllVendors(int page, int size, String sortBy, boolean asc);
}
