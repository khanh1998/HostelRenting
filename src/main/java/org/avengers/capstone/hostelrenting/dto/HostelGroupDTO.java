package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelGroupDTO implements Serializable {
    private int hostelGroupId;

    private String hostelGroupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;

    private int vendorId;
}
