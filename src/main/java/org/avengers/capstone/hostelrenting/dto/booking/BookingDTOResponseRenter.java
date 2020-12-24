package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 24/12/2020
 * @created 24/12/2020 - 08:58
 * @project youthhostelapp
 */
public class BookingDTOResponseRenter extends BookingDTOResponseVendor{

    @Override
    @JsonIgnore
    public String getQrCode() {
        return super.getQrCode();
    }
}
