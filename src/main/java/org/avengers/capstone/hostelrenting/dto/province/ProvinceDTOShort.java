package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;

import java.io.Serializable;
import java.util.List;

@Data
public class ProvinceDTOShort implements Serializable {
    private int provinceId;
    private String provinceName;
}
