package org.avengers.capstone.hostelrenting.dto.street;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 24/11/2020
 * @created 24/11/2020 - 10:27
 * @project youthhostelapp
 */
public class StreetDTOCreate extends StreetDTO{
    @Override
    @JsonIgnore
    public void setStreetId(Integer streetId) {
        super.setStreetId(streetId);
    }
}
