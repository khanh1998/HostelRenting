package org.avengers.capstone.hostelrenting.dto.hostelgroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTO;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.HostelGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class HostelGroupDTO implements Serializable {
    private int hostelGroupId;

    private Integer districtId;

//    private Integer vendorId;

    private String hostelGroupName;

    private String detailedAddress;

    private String longitude;

    private String latitude;



}
