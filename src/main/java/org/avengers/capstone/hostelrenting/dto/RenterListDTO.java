package capstone.tdhostel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RenterListDTO implements Serializable {
    private int renterId;

    private String renterName;

    private String phoneNumber;

    private String email;

    private String avatar;

    private String password;

    private List<Integer> bookingId;
}
