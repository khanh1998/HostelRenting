package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VendorDTO implements Serializable {
    private int vendorId;

    private String vendorName;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String password;
}
