package org.avengers.capstone.hostelrenting.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contract")
public class Contract {

    public enum STATUS{EXPIRED, WORKING}

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

    // month
    @Column(nullable = false)
    @NotNull(message = "Contract duration is mandatory")
    private Float duration;

    @Column
    private Integer dealId;

    @Column
    private Integer bookingId;

    @Column(columnDefinition = "varchar(10) default 'WORKING'")
    @Enumerated(EnumType.STRING)
    private Contract.STATUS status;


    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "contract_serDetail",
            joinColumns = @JoinColumn(name = "contractId"),
            inverseJoinColumns = @JoinColumn(name = "serDetailId")
    )
    private Collection<ServiceDetail> serDetails;
}
