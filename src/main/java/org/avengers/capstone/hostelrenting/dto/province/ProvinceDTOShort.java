package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProvinceDTOShort implements Serializable {
    @NotNull(message = "Province id is mandatory!")
    private Integer provinceId;
    private String provinceName;
}
