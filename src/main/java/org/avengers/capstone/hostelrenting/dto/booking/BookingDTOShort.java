package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Booking;

@Data
public class BookingDTOShort {
    private Integer bookingId;
    private Integer renterId;
    private Integer vendorId;
    private Integer typeId;
    private Booking.Status status;
    private Integer dealId;
    private String qrCode;
    private String meetTime;
}
