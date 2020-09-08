package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Address;
import org.avengers.capstone.hostelrenting.model.StreetWard;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.avengers.capstone.hostelrenting.util.AddressSerializer;

import java.io.Serializable;
import java.util.Collection;

@Data
public class HostelGroupDTO implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private Address address;

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

//    private Collection<HostelTypeDTO> hostelTypes;

//    private Collection<ServiceDTO> services;

}
