package org.avengers.capstone.hostelrenting.dto.facility;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FacilityDTO implements Serializable {
    private int facilityId;
    private String facilityName;
    private boolean isApproved;
}
