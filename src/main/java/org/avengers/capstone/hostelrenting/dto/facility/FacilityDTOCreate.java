package org.avengers.capstone.hostelrenting.dto.facility;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 15:49
 * @project youthhostelapp
 */
public class FacilityDTOCreate extends FacilityDTO{
    public FacilityDTOCreate() {
        this.setApproved(false);
    }

    @Override
    @JsonIgnore
    public void setFacilityId(int facilityId) {
        super.setFacilityId(facilityId);
    }

    @Override
    @JsonIgnore
    public void setApproved(boolean isApproved) {
        super.setApproved(isApproved);
    }
}
