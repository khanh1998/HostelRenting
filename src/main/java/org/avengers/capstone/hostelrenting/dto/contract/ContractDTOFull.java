package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;

/**
 * Contains all information that relative with contract
 */

@Data
public class ContractDTOFull {
    private int contractId;
    private HostelRoomDTO room;
    private ResTypeDTO type;
    private HostelGroupDTO group;
    private VendorDTOFull vendor;
    private RenterDTOFull renter;
    private Integer deal;
    private Integer booking;
    private long startTime;
    private int duration;
//    private boolean isDeleted;
}
