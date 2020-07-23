package org.avengers.capstone.hostelrenting.dto.vendor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class VendorDTOFull implements Serializable {
    private int vendorId;
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

//    private List<ContractDTO> contracts;
//    private List<HostelGroupDTO> hostelGroups;
//    private List<DealDTO> deals;
//    private List<BookingDTO> bookings;
}
