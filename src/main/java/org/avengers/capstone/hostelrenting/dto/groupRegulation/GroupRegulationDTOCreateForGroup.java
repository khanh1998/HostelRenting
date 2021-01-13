package org.avengers.capstone.hostelrenting.dto.groupRegulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 14:58
 * @project youthhostelapp
 */
@NoArgsConstructor
public class GroupRegulationDTOCreateForGroup extends RegulationDTO {
    @Override
    @JsonIgnore
    public String getRegulationName() {
        return super.getRegulationName();
    }

    @Override
    @JsonIgnore
    public boolean isApproved() {
        return super.isApproved();
    }

    public GroupRegulationDTOCreateForGroup(Integer regulationId) {
        this.setRegulationId(regulationId);
    }
}
