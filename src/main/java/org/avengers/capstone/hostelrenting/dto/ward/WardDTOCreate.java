package org.avengers.capstone.hostelrenting.dto.ward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 21/11/2020
 * @created 21/11/2020 - 11:43
 * @project youthhostelapp
 */
public class WardDTOCreate extends WardDTO {
    @Override
    @JsonIgnore
    public int getWardId() {
        return super.getWardId();
    }

    @Setter
    @Getter
    private int districtId;
}
