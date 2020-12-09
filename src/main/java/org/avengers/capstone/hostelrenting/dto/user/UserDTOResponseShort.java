package org.avengers.capstone.hostelrenting.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.User;

import java.util.UUID;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 07:28
 * @project youthhostelapp
 */
@Getter @Setter
public class UserDTOResponseShort {
    private UUID userId;
    private String username;
    private String avatar;
    private User.ROLE role;
    private String phone;
}
