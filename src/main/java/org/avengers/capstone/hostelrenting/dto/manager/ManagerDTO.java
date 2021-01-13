package org.avengers.capstone.hostelrenting.dto.manager;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:16
 * @project youthhostelapp
 */
@Getter
@Setter
public class ManagerDTO {
    private UUID managerId;
    private String managerName;
    private String managerPhone;
    private boolean isActive;
}
