package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.io.Serializable;

@Getter
@Setter
public class BookingDTOResponse implements Serializable {
    private int bookingId;
    private RenterDTOResponse renter;
    private VendorDTOResponse vendor;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private Booking.STATUS status;
    private DealDTOShort deal;
    private Integer contractId;
    private String qrCode;
    private Long meetTime;

    private long createdAt;
    private long updatedAt;
}
