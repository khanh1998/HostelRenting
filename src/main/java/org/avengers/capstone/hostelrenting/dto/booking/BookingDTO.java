package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.util.UUID;

/**
 * @author duattt on 11/12/20
 * @created 12/11/2020 - 09:50
 * @project youthhostelapp
 */
@Getter
@Setter
public class BookingDTO {
    private Integer bookingId;
    private RenterDTOResponse renter;
    private VendorDTOResponse vendor;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private DealDTOCreate deal;
    private Integer contractId;
    private Booking.STATUS status;
    private UUID qrCode;
    private Long meetTime;

    private Long createdAt;
    private Long updatedAt;
}
