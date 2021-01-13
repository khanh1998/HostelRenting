package org.avengers.capstone.hostelrenting.dto.district;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOResponse;

import java.util.List;

@Getter
@Setter
public class DistrictDTOResponse extends DistrictDTO {
    private List<WardDTOResponse> wards;
}
