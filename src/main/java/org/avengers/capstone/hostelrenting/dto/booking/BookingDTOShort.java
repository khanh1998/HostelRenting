package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Booking;

@Getter
@Setter
public class BookingDTOShort {
    private Integer bookingId;
    private Long renterId;
    private Long vendorId;
    private Integer typeId;
    private Booking.STATUS status;
    private Integer dealId;
    private String qrCode;
    private Long meetTime;
}
