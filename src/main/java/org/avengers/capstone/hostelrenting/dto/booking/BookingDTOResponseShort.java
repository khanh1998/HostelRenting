package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author duattt on 11/12/20
 * @created 12/11/2020 - 09:50
 * @project youthhostelapp
 */
@JsonIgnoreProperties({"renter","vendor","type","group","deal","contractId","qrCode","meetTime","updatedAt"})
public class BookingDTOResponseShort extends BookingDTO {
    @Override
    public Integer getBookingId() {
        return super.getBookingId();
    }
}
