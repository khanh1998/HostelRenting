package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

@Data
public class BookingResponseDTO {
    private int bookingId;
    private RenterDTO renter;
    private VendorDTO vendor;
    private HostelTypeDTO type;
    private BookingStatusDTO status;
    private DealDTO deal;
    private String qrCode;
    private String startTime;
}
