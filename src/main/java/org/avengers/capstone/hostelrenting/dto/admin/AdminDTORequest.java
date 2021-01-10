package org.avengers.capstone.hostelrenting.dto.admin;

import lombok.Data;

@Data
public class AdminDTORequest {
    private String password;
    private String phone;

    public AdminDTORequest(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }
}
