package org.avengers.capstone.hostelrenting.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDTOLogin {
    @NotEmpty(message = "Phone is mandatory")
    private String phone;
    @NotEmpty(message = "Password is mandatory")
    private String password;
}
