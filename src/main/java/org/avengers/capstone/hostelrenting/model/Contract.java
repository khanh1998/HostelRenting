package org.avengers.capstone.hostelrenting.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private HostelRoom hostelRoom;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    private Long startTime;

    @Column(nullable = false)
    @NotNull(message = "Contract duration is mandatory")
    private float duration;

    @Column
    private Integer dealId;

    @Column
    private Integer bookingId;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;


}