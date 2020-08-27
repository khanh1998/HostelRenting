package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Role;

@Data
public class RoleDTO {
    private int roleId;
    private Role.CODE code;
    private String roleName;
}
