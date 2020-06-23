package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vendor")
@Data
public class Vendor implements Serializable {
    @Id
    @Column(name = "vendor_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vendorId;

    @Column(name = "vendor_name", nullable = false, length = 100)
    private String vendorName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 12)
    private String phoneNumber;

    private String avatar;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @JsonManagedReference
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;

//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
//    private List<Deal> deals;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<HostelGroup> hostelGroups;
}
