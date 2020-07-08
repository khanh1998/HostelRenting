package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class ProvinceDTO {
    private int provinceId;
    private String provinceName;
    private List<DistrictDTO> districts;
}
