package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
//@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "renter")
public class Renter extends User{

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Deal> deals;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
