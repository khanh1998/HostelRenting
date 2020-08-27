package org.avengers.capstone.hostelrenting.dto.district;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;

import java.io.Serializable;

@Data
public class DistrictDTOShort implements Serializable {
    private int districtId;
    private String districtName;
    private ProvinceDTOShort province;
}
