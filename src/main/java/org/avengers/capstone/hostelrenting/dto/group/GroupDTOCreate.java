package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ServiceDetailDTO;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GroupDTOCreate implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private AddressFull addressFull;

    @NotNull(message = "vendorId is mandatory!")
    private Long vendorId;

    @NotNull(message = "groupName is mandatory!")
    private String groupName;

    @NotNull(message = "buildingNo is mandatory!")
    private String buildingNo;

    @NotNull(message = "longitude is mandatory!")
    private String longitude;

    @NotNull(message = "latitude is mandatory!")
    private String latitude;

    /**
     * curfewTime that limit the time to come back home
     */
    private String curfewTime;

    private boolean ownerJoin;

    private String imgUrl;

    private String managerName;

    private String managerPhone;

    private Float downPayment;

    private List<ServiceDetailDTO> services;

    private List<GroupRegulationDTOCreate> regulations;
}
