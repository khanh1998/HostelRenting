package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.HostelType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class FacilityDTO implements Serializable {
    private int facilityId;
    private String facilityName;
}
