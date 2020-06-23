package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RenterDTO implements Serializable {
    private int renterId;

    private String renterName;

    private String phoneNumber;

    private String email;

    private String avatar;

    private String password;
}
