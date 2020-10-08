package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOFull;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProvinceDTOFull extends ProvinceDTOShort implements Serializable {
    private List<DistrictDTOFull> districts;
}
