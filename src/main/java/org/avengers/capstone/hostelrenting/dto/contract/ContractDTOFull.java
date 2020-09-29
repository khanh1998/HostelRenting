package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;

/**
 * Contains all information that relative with contract
 */

@Data
public class ContractDTOFull {
    private int contractId;
    private HostelRoomDTO room;
    private ResTypeDTO type;
    private HostelGroupDTOFull group;
    private ResVendorDTO vendor;
    private ResRenterDTO renter;
    private DealDTOShort deal;
    private BookingDTOShort booking;
    private long startTime;
    private Float duration;

    private Long createdAt;
    private Long updatedAt;
}
