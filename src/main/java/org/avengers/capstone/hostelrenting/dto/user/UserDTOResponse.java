package org.avengers.capstone.hostelrenting.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.avengers.capstone.hostelrenting.dto.RoleDTO;
import org.avengers.capstone.hostelrenting.dto.Views;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter

public class UserDTOResponse implements Serializable {
    private Long userId;
    private String username;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String firebaseToken;
    private Long yearOfBirth;
    private String idIssuedLocation;
    private Long idIssuedDate;
    private String householdAddress;
    private String currentAddress;
    private String citizenIdNum;
    private String citizenIdFrontImg;
    private String citizenIdBackImg;
    private RoleDTO role;
    private String jwtToken;

    private Long createdAt;
    private Long updatedAt;
}
