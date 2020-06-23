package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VendorNoIdDTO implements Serializable {
    private String vendorName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String password;
}
