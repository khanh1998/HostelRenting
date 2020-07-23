package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTOFull {
    private int bookingId;
    private RenterDTOFull renter;
    private VendorDTOFull vendor;
    private HostelTypeDTO type;
    private HostelGroupDTO group;
    private BookingStatusDTO status;
    private DealDTOShort deal;
    private String qrCode;
    private String startTime;
}
