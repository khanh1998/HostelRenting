package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 16:46
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hostel_request")
public class HostelRequest {

    public enum STATUS{CREATED, DONE, EXPIRED}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private Float maxPrice;

    private Float minSuperficiality;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(columnDefinition = "integer default 5", nullable = false)
    private Double maxDistance;

    @Column(nullable = false)
    private long dueTime;

    @Column(columnDefinition = "varchar(10) default 'CREATED'", nullable = false)
    @Enumerated(EnumType.STRING)
    private HostelRequest.STATUS status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;
}
