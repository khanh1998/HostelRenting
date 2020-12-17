package org.avengers.capstone.hostelrenting.dto.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

/**
 * @author duattt on 14/12/2020
 * @created 14/12/2020 - 11:15
 * @project youthhostelapp
 */
public class ManagerDTOUpdate extends ManagerDTO{
    @Override
    @JsonIgnore
    public void setManagerId(UUID managerId) {
        super.setManagerId(managerId);
    }

    @Override
    @JsonIgnore
    public void setManagerName(String managerName) {
        super.setManagerName(managerName);
    }

    @Override
    @JsonIgnore
    public void setManagerPhone(String managerPhone) {
        super.setManagerPhone(managerPhone);
    }
}
