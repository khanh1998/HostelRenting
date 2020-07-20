package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.BookingStatus;

@Data
public class BookingDTOShort {
    private int bookingId;
    private int renterId;
    private int vendorId;
    private int typeId;
    private int statusId;
    private int dealId;
    private String qrCode;
    private String startTime;
}
