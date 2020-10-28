package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public class GroupDTOCreate implements Serializable {
    public GroupDTOCreate() {
        this.createdAt = System.currentTimeMillis();
    }

    @Getter
    @Setter
    private int groupId;

    @JsonProperty("address")
    @Getter
    @Setter
    private AddressFull addressFull;

    @NotNull(message = "vendorId is mandatory!")
    @Getter
    @Setter
    private Long vendorId;

    @NotNull(message = "groupName is mandatory!")
    @Getter
    @Setter
    private String groupName;

    @NotNull(message = "buildingNo is mandatory!")
    @Getter
    @Setter
    private String buildingNo;

    @NotNull(message = "longitude is mandatory!")
    @Getter
    @Setter
    private Double longitude;

    @NotNull(message = "latitude is mandatory!")
    @Getter
    @Setter
    private Double latitude;

    /**
     * curfewTime that limit the time to come back home
     */
    @Getter
    @Setter
    private String curfewTime;

    @Getter
    @Setter
    private boolean ownerJoin;

    @Getter
    @Setter
    private String imgUrl;

    @Getter
    @Setter
    private String managerName;

    @Getter
    @Setter
    private String managerPhone;

    @Getter
    @Setter
    private Float downPayment;

    @Getter
    @Setter
    private List<GroupServiceDTOCreate> services;

    @Getter
    @Setter
    private List<GroupRegulationDTOCreate> regulations;

    @Getter
    @Setter
    private List<GroupScheduleDTOCreate> schedules;

    @JsonIgnore
    @Getter
    private Long createdAt;
}
