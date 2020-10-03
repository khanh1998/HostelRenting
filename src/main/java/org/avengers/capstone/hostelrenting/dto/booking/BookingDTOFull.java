package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
import org.avengers.capstone.hostelrenting.model.Booking;

@Getter
@Setter
public class BookingDTOFull {
    private int bookingId;
    private ResRenterDTO renter;
    private ResVendorDTO vendor;
    private ResTypeDTO type;
    private HostelGroupDTOFull group;
    private Booking.STATUS status;
    private DealDTOShort deal;
    private String qrCode;
    private Long meetTime;

    private long createdAt;
    private long updatedAt;
}
