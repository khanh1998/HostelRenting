package org.avengers.capstone.hostelrenting.dto.groupRegulation;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 12:52
 * @project youthhostelapp
 */
public class GroupRegulationDTOCreate extends RegulationDTO {
    public GroupRegulationDTOCreate() {
        this.setApproved(false);
    }

    @JsonIgnore
    @Override
    public int getRegulationId() {
        return super.getRegulationId();
    }

    @JsonIgnore
    @Override
    public boolean isApproved() {
        return super.isApproved();
    }
}
