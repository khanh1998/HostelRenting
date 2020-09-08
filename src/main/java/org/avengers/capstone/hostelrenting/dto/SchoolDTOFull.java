package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;

@Data
public class SchoolDTOFull {
    private int schoolId;
    private String schoolName;
    private String longitude;
    private String latitude;
    @JsonProperty("address")
    private DistrictDTOShort district;
}