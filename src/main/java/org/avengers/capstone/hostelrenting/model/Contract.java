package org.avengers.capstone.hostelrenting.model;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 09:32
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "contract")
public class Contract {

    public enum STATUS{EXPIRED, WORKING}

    /**
     * contract id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    /**
     * hostel room object
     */
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

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

    /**
     * contract duration
     */
    @Column(nullable = false)
    private Float duration;

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
     * image url of contract evidence
     */
    private String evidenceImgUrl;

    /**
     * contract status
     */
    @Column(columnDefinition = "varchar(10) default 'WORKING'")
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
     * Service details
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "contract_serDetail",
            joinColumns = @JoinColumn(name = "contractId"),
            inverseJoinColumns = @JoinColumn(name = "serDetailId")
    )
    private Collection<ServiceDetail> serDetails;
}
