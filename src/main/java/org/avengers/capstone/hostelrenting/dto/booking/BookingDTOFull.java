package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTO;

@Data
public class BookingDTOFull {
    private int bookingId;
    private RenterDTO renter;
    private VendorDTO vendor;
    private HostelTypeDTO type;
    private BookingStatusDTO status;
    private DealDTOShort deal;
    private String qrCode;
    private String startTime;
}
