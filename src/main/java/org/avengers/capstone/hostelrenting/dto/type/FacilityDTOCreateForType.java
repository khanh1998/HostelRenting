package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTO;

/**
 * @author duattt on 11/11/20
 * @created 11/11/2020 - 10:23
 * @project youthhostelapp
 */
public class FacilityDTOCreateForType extends FacilityDTO {
    @Override
    @JsonIgnore
    public void setFacilityName(String facilityName) {
        super.setFacilityName(facilityName);
    }
}
