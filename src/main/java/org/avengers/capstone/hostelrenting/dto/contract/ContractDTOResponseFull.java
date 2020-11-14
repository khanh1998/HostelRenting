package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOResponse;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOFull;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupServiceDTOResponse;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTO;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponseShort;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.util.Collection;
import java.util.UUID;

/**
 * @author duattt on 11/11/20
 * @created 11/11/2020 - 19:45
 * @project youthhostelapp
 */
@Getter
@Setter
public class ContractDTOResponseFull {
    private int contractId;
    private RoomDTO room;
    private TypeDTOResponse type;
    @JsonIgnoreProperties(value = "appendixContract")
    private GroupDTOResponse group;
    private VendorDTOResponse vendor;
    private RenterDTOResponse renter;
    private DealDTOShort deal;
    private BookingDTOResponseShort booking;
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