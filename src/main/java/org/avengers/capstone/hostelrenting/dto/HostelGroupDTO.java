package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class HostelGroupDTO implements Serializable {
    private int groupId;

    private Integer wardId;

    private Integer vendorId;

    private String groupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;

    private Collection<HostelTypeDTO> hostelTypes;

    private Collection<ServiceDTO> services;

}
