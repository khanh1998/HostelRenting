package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.HostelTypeDTO;
import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTO;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealDTOFull {
    private int dealId;
    private HostelTypeDTO type;
    private HostelGroupDTO group;
    private RenterDTO renter;
    private VendorDTO vendor;
    private float offeredPrice;
}
