package org.avengers.capstone.hostelrenting.model;


import lombok.*;

import javax.persistence.*;
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
@Table(name = "contract")
public class Contract {

    public enum STATUS{EXPIRED, ACTIVATED, INACTIVE, REVERSED }

    /**
     * contract id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    /**
     * appendix contract string for each group
     */
    @Column(length = 5000)
    private String appendixContract;

    /**
     * hostel room object
     */
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

    /**
     * vendor object
     */
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * renter object
     */
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<ContractImage> contractImages;

    /**
     * contract duration
     */
    @Column(nullable = false)
    private Integer duration;

    /**
     * start time of duration
     */
    private Long startTime;

    /**
     * deal id for tracking
     */
    @Column
    private Integer dealId;

    /**
     * booking id for tracking
     */
    @Column
    private Integer bookingId;


    /**
     * qrCode to scan
     */
    private UUID qrCode;

    private String contractUrl;

    /**
     * contract status
     */
    @Column(columnDefinition = "varchar(15) default 'INACTIVE'")
    @Enumerated(EnumType.STRING)
    private Contract.STATUS status;

    /**
     * creating timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    /**
     * updating timestamp
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * Group services
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "group_service_contract",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "group_service_id")
    )
    private Collection<GroupService> groupServices;

}
