package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Deal;

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
    private DealDTOShort deal;
    private BookingDTOShort booking;
    private long startTime;
    private Float duration;

    private Long createdAt;
    private Long updatedAt;
}
