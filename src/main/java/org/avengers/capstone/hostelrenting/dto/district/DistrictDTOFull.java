package org.avengers.capstone.hostelrenting.dto.district;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOFull;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DistrictDTOFull implements Serializable {
    private int districtId;
    private String districtName;
    private List<WardDTOFull> wards;
}
