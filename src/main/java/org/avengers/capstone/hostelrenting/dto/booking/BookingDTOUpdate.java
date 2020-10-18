package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author duattt on 10/18/20
 * @created 18/10/2020 - 10:48
 * @project youthhostelapp
 */
public class BookingDTOUpdate implements Serializable {
    public BookingDTOUpdate(){
        this.updatedAt = System.currentTimeMillis();
    }

    @Getter @Setter
    @NotNull(message = "QrCode id is mandatory!")
    private UUID qrCode;

    @Getter @Setter
    @NotNull(message = "Status id is mandatory!")
    private Booking.STATUS status;

    @Getter
    private final Long updatedAt;
}
