package org.avengers.capstone.hostelrenting.dto.district;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;

import java.io.Serializable;

@Getter
@Setter
public class DistrictDTOShort implements Serializable {
    private int districtId;
    private String districtName;
    private ProvinceDTO province;
}
