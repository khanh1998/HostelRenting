package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupServiceDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponseShort;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.util.Collection;
import java.util.UUID;

/**
 * Contains all information that relative with contract
 */

@Getter
@Setter
public class ContractDTOResponse {
    private int contractId;
    private RoomDTO room;
    private TypeDTOResponseShort type;
    @JsonIgnoreProperties(value = "appendixContract")
    private GroupDTOResponseShort group;
    private VendorDTOResponseShort vendor;
    private RenterDTOResponseShort renter;
    private DealDTOShort deal;
    private BookingDTOCreate booking;
    private String appendixContract;
    private long startTime;
    private Float duration;
    private String contractUrl;
    private UUID qrCode;
    private Contract.STATUS status;
    @JsonProperty(value = "agreementServices")
    private Collection<GroupServiceDTOResponse> groupServices;
    @JsonProperty(value = "images")
    private Collection<ImageDTOResponse> contractImages;

    private Long createdAt;
    private Long updatedAt;
}
