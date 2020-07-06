package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Province;

import java.io.Serializable;

@Data
public class DistrictDTO implements Serializable {
    private int districtId;

    private String districtName;

    @JsonIgnore
    private Province province;
//
//    private int hostelGroupId;
}
