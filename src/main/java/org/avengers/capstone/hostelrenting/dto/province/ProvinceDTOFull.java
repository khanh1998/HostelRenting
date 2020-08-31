package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ProvinceDTOFull implements Serializable {
    private int provinceId;
    private String provinceName;
    private List<DistrictDTOFull> districts;
}
