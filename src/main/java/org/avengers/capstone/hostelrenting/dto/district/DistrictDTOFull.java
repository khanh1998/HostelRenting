package org.avengers.capstone.hostelrenting.dto.district;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOFull;

import java.io.Serializable;
import java.util.List;

@Data
public class DistrictDTOFull implements Serializable {
    private int districtId;
    private String districtName;
    private List<WardDTOFull> wards;
}
