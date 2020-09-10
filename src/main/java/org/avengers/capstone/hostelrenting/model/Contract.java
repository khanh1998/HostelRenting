package org.avengers.capstone.hostelrenting.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contract")
public class Contract {

//    public enum DURATION_UNIT{MONTH, YEAR}

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
    private Float duration;

    @Column(nullable = false)
    @NotNull(message = "Contract duration is mandatory")
    private Float minDuration;

    @Column(columnDefinition = "varchar(10) default 'tháng'", nullable = false)
    private String durationUnit;

    @Column
    private Integer dealId;

    @Column
    private Integer bookingId;


    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "contract_serDetail",
            joinColumns = @JoinColumn(name = "contractId"),
            inverseJoinColumns = @JoinColumn(name = "serDetailId")
    )
    private Collection<ServiceDetail> serDetails;
}
