package org.avengers.capstone.hostelrenting.dto.district;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.avengers.capstone.hostelrenting.model.District;

/**
 * @author duattt on 11/18/20
 * @created 18/11/2020 - 12:08
 * @project youthhostelapp
 */
public class DistrictDTOCreate extends DistrictDTO {
    @Override
    @JsonIgnore
    public int getDistrictId() {
        return super.getDistrictId();
    }
}
