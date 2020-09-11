package org.avengers.capstone.hostelrenting.dto.hostelgroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import java.io.Serializable;

@Data
public class HostelGroupDTOShort implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private AddressFull addressFull;

    private Integer vendorId;

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
}
