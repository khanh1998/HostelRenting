package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Role;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private int roleId;
    private Role.CODE code;
    private String roleName;
}
