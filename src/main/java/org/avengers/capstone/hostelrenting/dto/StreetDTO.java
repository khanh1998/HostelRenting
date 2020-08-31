package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class StreetDTO implements Serializable {
    private Integer streetId;
    private String streetName;
//    private List<WardDTO> wards;
}
