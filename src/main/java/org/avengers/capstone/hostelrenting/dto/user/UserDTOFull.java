package org.avengers.capstone.hostelrenting.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.ToString;
import org.avengers.capstone.hostelrenting.dto.RoleDTO;
import org.avengers.capstone.hostelrenting.dto.Views;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data

public class UserDTOFull implements Serializable {
    private int userId;
    @NotEmpty(message = "Username is mandatory")
    private String username;
    @NotEmpty(message = "Password is mandatory")
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @Email
    @NotEmpty(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Phone is mandatory")
    private String phone;
    private String avatar;
    private String firebaseToken;
    private RoleDTO role;
    @JsonView({Views.Internal.class})
    private String jwtToken;
}
