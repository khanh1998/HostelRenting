package org.avengers.capstone.hostelrenting.dto.vendor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOResponse;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class VendorDTOResponse extends UserDTOResponse implements Serializable {
    private boolean isBlocked;
    private boolean isCensored;
//    private List<ContractDTOShort> contracts;
//    private List<HostelGroupDTO> hostelGroups;
//    private List<DealDTOCreate> deals;
//    private List<BookingDTOShort> bookings;
}
