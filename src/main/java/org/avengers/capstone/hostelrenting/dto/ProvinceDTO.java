package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.DistrictDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class ProvinceDTO {
    private int provinceId;
    private String provinceName;
    private List<DistrictDTO> districts;
}
