package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Vendor;

public interface VendorService {
    void checkExist(Long id);

    Vendor updateInfo(Vendor exModel, VendorDTOUpdate reqDTO);
    Vendor updateToken(Vendor exModel, UserDTOUpdateOnlyToken reqDTO);
    Vendor findById(Long id);
    Vendor create(Vendor vendor);
}
