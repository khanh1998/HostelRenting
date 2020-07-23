package org.avengers.capstone.hostelrenting.dto.vendor;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VendorDTOLogin {
    @NotEmpty(message = "Phone is mandatory")
    private String phone;
    @NotEmpty(message = "Password is mandatory")
    private String password;
}
