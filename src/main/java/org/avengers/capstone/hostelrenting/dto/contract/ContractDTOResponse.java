package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.group.GroupServiceDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
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
    private HostelRoomDTO room;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private ResVendorDTO vendor;
    private RenterDTOResponse renter;
    private DealDTOShort deal;
    private BookingDTOCreate booking;
    private long startTime;
    private Float duration;
    private String evidenceImgUrl;
    private String contractUrl;
    private UUID qrCode;
    private Contract.STATUS status;
    @JsonProperty(value = "agreementServices")
    private Collection<GroupServiceDTOResponse> groupServices;

    private Long createdAt;
    private Long updatedAt;
}
