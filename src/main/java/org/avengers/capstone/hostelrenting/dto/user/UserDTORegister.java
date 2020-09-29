package org.avengers.capstone.hostelrenting.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author duattt on 9/29/20
 * @created 29/09/2020 - 10:09
 * @project youthhostelapp
 */
@Data
public class UserDTORegister {
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotEmpty(message = "Phone is mandatory")
    private String phone;
}
