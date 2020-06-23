package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingDTO implements Serializable {
    private int bookingId;

    private int dealId;

    private String startTime;

    private String endTime;

    private String status;

    private int renterId;

    private int vendorId;

    private int hostelTypeId;
}
