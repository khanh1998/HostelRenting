package org.avengers.capstone.hostelrenting.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 09:32
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contract")
public class Contract {

    public enum STATUS{EXPIRED, ACTIVATED, INACTIVE, RESERVED, CANCELLED, ACCEPTED}
    public enum RESIGN{REQUEST, AGREE, REJECT}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    @Column(length = 6000)
    private String appendixContract;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Transient
    private Type type;
    @Transient
    private Group group;
    @Transient
    private Deal deal;
    @Transient
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<ContractImage> contractImages;

    @Column(nullable = false)
    private Integer duration;

    private Long startTime;

    private Integer dealId;

    private Integer bookingId;

    private Float downPayment;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isReserved;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isPaid;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Contract.RESIGN resign;

    private UUID qrCode;

    private String contractUrl;

    @Column(nullable = false)
    @Min(1)
    @Max(31)
    private Integer paymentDayInMonth;

    @Column(columnDefinition = "varchar(15) default 'INACTIVE'")
    @Enumerated(EnumType.STRING)
    private Contract.STATUS status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Long createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Long updatedAt;

    private Long lastPayAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "group_service_contract",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "group_service_id")
    )
    private Collection<GroupService> groupServices;

//    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Collection<GroupServiceContract> groupServiceContracts;
}
