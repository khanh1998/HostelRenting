package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelTypeDTO implements Serializable {
    private int hostelTypeId;

    private String hostelName;

    private String price;

    private String unit;

    private float superficiality;

    private int capacity;

    private int bookingId;

    private int hostelGroupId;
}
