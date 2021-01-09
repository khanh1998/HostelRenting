package org.avengers.capstone.hostelrenting.dto.facility;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FacilityDTO implements Serializable {
    private int facilityId;
    private int quantity;
    private String facilityName;
    private boolean isApproved;
}
