package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avengers.capstone.hostelrenting.model.HostelType;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
public class HostelGroupDTO implements Serializable {
    private int groupId;

    private Integer wardId;

//    private Integer vendorId;

    private String groupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;

    private List<HostelTypeDTO> hostelTypes;



}
