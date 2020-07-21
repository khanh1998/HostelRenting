package org.avengers.capstone.hostelrenting.dto.deal;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.HostelTypeDTO;
import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTO;

@Data
public class DealDTOFull {
    private int dealId;
    private HostelTypeDTO type;
    private RenterDTO renter;
    private VendorDTO vendor;
    private float offeredPrice;
}
