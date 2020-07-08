package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DistrictDTO implements Serializable {
    private int districtId;
    private String districtName;
    private Integer provinceId;
    private List<WardDTO> wards;
}
