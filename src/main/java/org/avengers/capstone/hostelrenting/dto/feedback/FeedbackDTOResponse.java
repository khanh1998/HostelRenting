package org.avengers.capstone.hostelrenting.dto.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOResponseShort;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.io.Serializable;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 07:11
 * @project youthhostelapp
 */
@Getter
@Setter
public class FeedbackDTOResponse implements Serializable {
    private Integer feedbackId;
    private UserDTOResponseShort renter;
    private TypeDTOResponseShort type;
    private String subject;
    private String comment;
    private Integer rating;
    @JsonIgnore
    private Booking booking;
    private Integer bookingId;
    private Long bookingTimestamp;
    @JsonIgnore
    private Contract contract;
    private Integer contractId;
    private Long contractTimestamp;
    private Long createdAt;
    private Long updatedAt;

    public Long getBookingTimestamp() {
        if (booking != null)
            return booking.getCreatedAt();
        return null;
    }

    public Long getContractTimestamp() {
        if (contract != null)
            return contract.getCreatedAt();
        return null;
    }

    public Integer getBookingId() {
        if (booking != null)
            return booking.getBookingId();
        return null;

    }

    public Integer getContractId() {
        if (contract != null)
            return contract.getContractId();
        return null;
    }
}
