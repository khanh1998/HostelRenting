package org.avengers.capstone.hostelrenting.dto.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:16
 * @project youthhostelapp
 */
public class ManagerDTOCreate extends ManagerDTO{
    @Override
    @JsonIgnore
    public void setUserId(Long userId) {
        super.setUserId(userId);
    }
}
