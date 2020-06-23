package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RenterNoIdDTO implements Serializable {
    private String renterName;

    private String phoneNumber;

    private String email;

    private String avatar;

    private String password;
}
