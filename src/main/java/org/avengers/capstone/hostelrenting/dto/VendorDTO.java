package capstone.tdhostel.dto;

import capstone.tdhostel.model.Schedule;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class VendorDTO implements Serializable {
    private int vendorId;

    private String vendorName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String password;
}
