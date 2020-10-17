package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;
import org.avengers.capstone.hostelrenting.model.serialized.ServiceFull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupDTOResponse implements Serializable {
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

    private float downPayment;

    @JsonProperty("services")
    private Collection<GroupServiceDTOResponse> groupServices;

    @JsonProperty("regulations")
    private Collection<GroupRegulationDTOResponse> groupRegulations;

    public Collection<GroupRegulationDTOResponse> getGroupRegulations() {
        return groupRegulations.stream().filter(GroupRegulationDTOResponse::isActive).collect(Collectors.toList());
    }
}