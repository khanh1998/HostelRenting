package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;

import java.io.Serializable;

@Getter
@Setter
public class SchoolDTOFull implements Serializable {
    private int schoolId;
    private String schoolName;
    private String longitude;
    private String latitude;
    @JsonProperty("address")
    private DistrictDTOShort district;
}
