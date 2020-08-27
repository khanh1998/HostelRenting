package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class HostelGroupDTO implements Serializable {
    private int groupId;

    private StreetDTO street;

    private Integer vendorId;

    private String groupName;

    private String buildingNo;

    private String longitude;

    private String latitude;

    /**
     * curfewTime that limit the time to come back home
     */
    private String curfewTime;

    private String description;

    private boolean ownerJoin;

//    private Collection<HostelTypeDTO> hostelTypes;

//    private Collection<ServiceDTO> services;

}
