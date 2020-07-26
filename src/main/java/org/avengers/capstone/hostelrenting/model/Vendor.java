package org.avengers.capstone.hostelrenting.model;

import lombok.*;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOFull;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
//@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "vendor")
public class Vendor extends User{

    public Vendor(){
        contracts = null;
        deals = null;
        bookings = null;
        hostelGroups = null;
    }

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Deal> deals;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelGroup> hostelGroups;
}
