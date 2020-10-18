package org.avengers.capstone.hostelrenting.dto.vendor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOResponse;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResVendorDTO extends UserDTOResponse implements Serializable {

//    private List<ContractDTOShort> contracts;
//    private List<HostelGroupDTO> hostelGroups;
//    private List<DealDTOShort> deals;
//    private List<BookingDTOShort> bookings;
}
