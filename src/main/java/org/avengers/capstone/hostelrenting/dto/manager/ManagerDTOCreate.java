package org.avengers.capstone.hostelrenting.dto.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:16
 * @project youthhostelapp
 */
public class ManagerDTOCreate extends ManagerDTO{

    public ManagerDTOCreate() {
        setActive(true);
    }

    @Override
    @JsonIgnore
    public void setManagerId(UUID managerId) {
        super.setManagerId(managerId);
    }

    @Override
    @NotNull(message = "Manager name is mandatory")
    public String getManagerName() {
        return super.getManagerName();
    }

    @Override
    @JsonIgnore
    public void setActive(boolean isActive) {
        super.setActive(isActive);
    }
}
