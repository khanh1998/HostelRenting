package org.avengers.capstone.hostelrenting.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author duattt on 10/23/20
 * @created 23/10/2020 - 14:40
 * @project youthhostelapp
 */

public class UserDTOUpdate implements Serializable {

    public UserDTOUpdate(){
        this.updatedAt = System.currentTimeMillis();
    }

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @NotNull(message = "Year of birth is mandatory!")
    private Integer yearOfBirth;

    @Getter
    @Setter
    @NotNull(message = "Citizen id - issued location is mandatory!")
    private String idIssuedLocation;

    @Getter
    @Setter
    @NotNull(message = "Citizen id - issued date is mandatory!")
    private Long idIssuedDate;

    @Getter
    @Setter
    @NotNull(message = "Household address is mandatory!")
    private String householdAddress;

    @Getter
    @Setter
    @NotNull(message = "Email is mandatory!")
    private String email;

    @Getter
    @Setter
    @NotNull(message = "Current address is mandatory!")
    private String currentAddress;

    @Getter
    @Setter
    @NotNull(message = "Citizen id number is mandatory!")
    private String citizenIdNum;

    @Getter
    @Setter
    @NotNull(message = "Citizen id front image is mandatory!")
    private String citizenIdFrontImg;

    @Getter
    @Setter
    @NotNull(message = "Citizen id back image is mandatory!")
    private String citizenIdBackImg;

    @Getter
    @Setter
    @NotNull(message = "Avatar is mandatory!")
    private String avatar;

    @Getter
    @Setter
    private String firebaseToken;

    @Getter
    @JsonIgnore
    private Long updatedAt;
}
