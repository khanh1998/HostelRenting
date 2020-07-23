package org.avengers.capstone.hostelrenting.dto.renter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
public class RenterDTOFull {
    private int renterId;
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

//    private Collection<BookingDTO> bookings;
//    private Collection<DealDTO> deals;
//    private Collection<ContractDTO> contracts;
//    private Collection<RequestDTO> requests;
//    private Collection<ConversationDTO> conversations;
}
