package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.BookingStatus;

@Data
public class BookingDTO {
    private int bookingId;
    private int renterId;
    private int vendorId;
    private int typeId;
    private int statusId;
    private int dealId;
    private String qrCode;
    private String startTime;
}
