package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.model.Booking;

@Data
public class BookingDTOFull {
    private int bookingId;
    private RenterDTOFull renter;
    private VendorDTOFull vendor;
    private ResTypeDTO type;
    private HostelGroupDTO group;
    private Booking.STATUS status;
    private DealDTOShort deal;
    private String qrCode;
    private Long meetTime;

    private long createdAt;
    private long updatedAt;
}
