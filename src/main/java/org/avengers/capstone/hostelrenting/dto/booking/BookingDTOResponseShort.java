package org.avengers.capstone.hostelrenting.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;

import java.util.UUID;

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
