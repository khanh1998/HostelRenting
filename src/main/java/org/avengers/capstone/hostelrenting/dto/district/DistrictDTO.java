package org.avengers.capstone.hostelrenting.dto.district;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;

import java.io.Serializable;

@Data
public class DistrictDTO implements Serializable {
    private int districtId;

    private String districtName;

    @JsonIgnore
    private ProvinceDTO provinceId;
}
