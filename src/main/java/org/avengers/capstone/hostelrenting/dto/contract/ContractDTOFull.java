package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;

/**
 * Contains all information that relative with contract
 */

@Getter
@Setter
public class ContractDTOFull {
    private int contractId;
    private HostelRoomDTO room;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private ResVendorDTO vendor;
    private ResRenterDTO renter;
    private DealDTOShort deal;
    private BookingDTOShort booking;
    private long startTime;
    private Float duration;

    private Long createdAt;
    private Long updatedAt;
}
