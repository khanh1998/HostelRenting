package org.avengers.capstone.hostelrenting.dto.vendor;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VendorDTO implements Serializable {
    private int vendorId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;

//    private List<ContractDTO> contracts;
//    private List<HostelGroupDTO> hostelGroups;
//    private List<DealDTO> deals;
//    private List<BookingDTO> bookings;
}
