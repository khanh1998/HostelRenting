package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DistrictListDTO implements Serializable {
    private int districtId;

    private String districtName;

    private int provinceId;

    private List<Integer> hostelGroupId;
}
