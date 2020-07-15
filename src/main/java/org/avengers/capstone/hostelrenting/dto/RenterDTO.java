package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class RenterDTO {
    private int renterId;
    private String username;
    private String phone;
    private String email;
    private String avatar;
    private String password;

//    private Collection<BookingDTO> bookings;
//    private Collection<DealDTO> deals;
//    private Collection<ContractDTO> contracts;
//    private Collection<RequestDTO> requests;
//    private Collection<ConversationDTO> conversations;
}
