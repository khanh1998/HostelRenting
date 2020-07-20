package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class VendorDTO implements Serializable {
    private int vendorId;
    private String username;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String phone;
    private String avatar;

//    private List<ContractDTO> contracts;
//    private List<HostelGroupDTO> hostelGroups;
//    private List<DealDTO> deals;
//    private List<BookingDTO> bookings;
}
