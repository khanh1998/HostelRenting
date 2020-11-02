package org.avengers.capstone.hostelrenting.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author duattt on 9/29/20
 * @created 29/09/2020 - 10:09
 * @project youthhostelapp
 */

public class UserDTORegister implements Serializable {

    public UserDTORegister() {
        this.createdAt = System.currentTimeMillis();
    }



    @Getter
    @Setter
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @Getter
    @Setter
    @NotEmpty(message = "Password is mandatory")
    private String password;

    @Getter
    @Setter
    @NotEmpty(message = "Phone is mandatory")
    private String phone;

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private Integer yearOfBirth;
    @Getter
    @Setter
    private String idIssuedLocation;
    @Getter
    @Setter
    private Long idIssuedDate;
    @Getter
    @Setter
    private String householdAddress;
    @Getter
    @Setter
    private String currentAddress;
    @Getter
    @Setter
    private String citizenIdNum;
    @Getter
    @Setter
    private String citizenIdFrontImg;
    @Getter
    @Setter
    private String citizenIdBackImg;

    @JsonIgnore
    @Getter
    private Long createdAt;


}
