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

    public UserDTORegister(){
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

    @JsonIgnore
    @Getter
    private Long createdAt;
}
