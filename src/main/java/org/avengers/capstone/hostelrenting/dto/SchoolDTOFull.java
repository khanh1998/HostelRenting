package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class SchoolDTOFull implements Serializable {
    @NotNull(message = "School id is mandatory!")
    private Integer schoolId;
    private String schoolName;
    private Double longitude;
    private Double latitude;
    @JsonProperty("address")
    private DistrictDTOShort district;
}
