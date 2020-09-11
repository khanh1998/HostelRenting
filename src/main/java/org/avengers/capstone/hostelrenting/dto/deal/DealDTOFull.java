package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.model.Deal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealDTOFull {
    private int dealId;
    private Deal.STATUS status;
    private ResTypeDTO type;
    private HostelGroupDTOFull group;
    private RenterDTOFull renter;
    private VendorDTOFull vendor;
    private float offeredPrice;

    private long createdAt;
    private long updatedAt;
}
