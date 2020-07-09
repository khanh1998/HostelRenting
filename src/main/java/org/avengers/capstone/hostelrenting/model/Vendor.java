package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.ContractDTO;
import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username name is mandatory")
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String avatar;


//    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Contract> contracts;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelGroup> hostelGroups;
}
