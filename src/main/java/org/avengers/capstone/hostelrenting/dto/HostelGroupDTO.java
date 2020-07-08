package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class HostelGroupDTO implements Serializable {
    private int hostelGroupId;

    private Integer wardId;

//    private Integer vendorId;

    private String hostelGroupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;



}
