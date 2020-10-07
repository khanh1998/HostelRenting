package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FacilityDTO implements Serializable {
    private int facilityId;
    private String facilityName;
}
