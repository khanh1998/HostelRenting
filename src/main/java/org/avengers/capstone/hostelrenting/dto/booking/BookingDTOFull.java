package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
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
    private HostelGroupDTOFull group;
    private Booking.STATUS status;
    private DealDTOShort deal;
    private String qrCode;
    private Long meetTime;

    private long createdAt;
    private long updatedAt;
}
