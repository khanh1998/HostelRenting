package org.avengers.capstone.hostelrenting.dto.vendor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private List<ContractDTOShort> contracts;
    private List<HostelGroupDTO> hostelGroups;
    private List<DealDTOShort> deals;
    private List<BookingDTOShort> bookings;
}
