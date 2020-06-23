package org.avengers.capstone.hostelrenting.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "renter")
@Data
public class Renter implements Serializable {
    @Id
    @Column(name = "renter_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int renterId;

    @Column(name = "renter_name",nullable = false, length = 100)
    private String renterName;

    @Column(name = "phone_number", nullable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    private String avatar;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    private List<capstone.tdhostel.model.Booking> bookings;
//
//    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
//    private List<Deal> deals;
}
