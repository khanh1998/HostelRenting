package org.avengers.capstone.hostelrenting.dto.hostelgroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ServiceDetailDTO;
import org.avengers.capstone.hostelrenting.model.ServiceDetail;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HostelGroupDTOShort implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private AddressFull addressFull;

    private Long vendorId;

    private String groupName;

    private String buildingNo;

    private String longitude;

    private String latitude;

    /**
     * curfewTime that limit the time to come back home
     */
    private String curfewTime;

    private boolean ownerJoin;

    private String imgUrl;

    private List<ServiceDetailDTO> services;
}
