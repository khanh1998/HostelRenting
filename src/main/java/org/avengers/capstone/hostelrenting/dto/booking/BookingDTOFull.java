package org.avengers.capstone.hostelrenting.dto.booking;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
import org.avengers.capstone.hostelrenting.model.Booking;

@Getter
@Setter
public class BookingDTOFull {
    private int bookingId;
    private ResRenterDTO renter;
    private ResVendorDTO vendor;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private Booking.STATUS status;
    private DealDTOShort deal;
    private String qrCode;
    private Long meetTime;

    private long createdAt;
    private long updatedAt;
}
