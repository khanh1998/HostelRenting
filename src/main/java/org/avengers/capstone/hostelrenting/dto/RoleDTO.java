package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Role;

import java.io.Serializable;

@Getter
@Setter
public class RoleDTO implements Serializable {
    private int roleId;
    private Role.CODE code;
    private String roleName;
}
