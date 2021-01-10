package org.avengers.capstone.hostelrenting.dto.admin;


import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AdminResponse implements Serializable {
    private UUID userId;
    private String password;
    private String phone;
    private String jwtToken;
}
