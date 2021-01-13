package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Booking;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class BookingDTOCreate {
    public BookingDTOCreate() {
        this.qrCode = UUID.randomUUID();
        this.status = Booking.STATUS.INCOMING;
        this.createdAt = System.currentTimeMillis();
    }

    @Getter
    @Setter
    @JsonIgnore
    private Integer bookingId;

    @Getter
    @Setter
    @NotNull(message = "Renter id is mandatory!")
    private UUID renterId;

    @Getter
    @Setter
    @NotNull(message = "Vendor id is mandatory!")
    private UUID vendorId;

    @Getter
    @Setter
    @NotNull(message = "Type id is mandatory!")
    private Integer typeId;

    @Getter
    @Setter
    private Integer dealId;

    @Getter
    @Setter
    @NotNull(message = "Meet time is mandatory!")
    private Long meetTime;

    @Getter
    @JsonIgnore
    private UUID qrCode;

    @JsonIgnore
    @Getter
    private Long createdAt;

    @Getter
    @JsonIgnore
    private Booking.STATUS status;
}
