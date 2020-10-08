package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "booking")
public class Booking {
    public enum STATUS{INCOMING, MISSING, DONE, CANCELLED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @Column(columnDefinition = "varchar(10) default 'INCOMING'")
    @Enumerated(EnumType.STRING)
    private STATUS status;

    @Column(columnDefinition = "int default null")
    private Integer dealId;

    @Column
    private String qrCode;

    @Column(nullable = false)
    private Long meetTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}