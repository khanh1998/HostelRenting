package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;

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
