package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 10:13
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "feedback")
public class Feedback {
    /**
     * feedback id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;


    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    private String comment;

    @Column(nullable = false)
    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 5, message = "must be equal or less than 5")
    private Integer rating;

    @Transient
    private Booking booking;

    private Integer bookingId;

    @Transient
    private Contract contract;

    private Integer contractId;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<FeedbackImage> feedbackImages;

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

    @Column(columnDefinition = "bool default false")
    private boolean isDeleted;


    public void setBooking(Booking booking) {
        this.booking = booking;
        this.bookingId = booking.getBookingId();
    }

    public void setContract(Contract contract) {
        this.contract = contract;
        this.contractId = contract.getContractId();
    }
}
