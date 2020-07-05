package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HostelGroupDTO implements Serializable {
    private int hostelGroupId;

    private String hostelGroupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;

    private int districtId;
//
//    private List<Integer> scheduleIds;
}
