package org.avengers.capstone.hostelrenting.dto.renter;

import javax.validation.constraints.NotEmpty;

public class RenterDTOLogin {
    @NotEmpty(message = "Username is mandatory")
    private String username;
    @NotEmpty(message = "Password is mandatory")
    private String password;
}
