package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "deal")
public class Deal {
    public enum Status{CREATED, DONE, CANCELED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealId;

    @Column(columnDefinition = "varchar(10) default 'CREATED'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private HostelType hostelType;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @Column
    @NotNull(message = "Offered price is mandatory")
    private Float offeredPrice;

    private Long creationTime;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;
}
