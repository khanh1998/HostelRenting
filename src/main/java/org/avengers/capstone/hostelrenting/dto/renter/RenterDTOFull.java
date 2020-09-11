package org.avengers.capstone.hostelrenting.dto.renter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.SchoolDTOFull;
import org.avengers.capstone.hostelrenting.dto.district.DistrictDTOShort;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;

import java.io.Serializable;

@Data
public class RenterDTOFull extends UserDTOFull implements Serializable  {

    private String idNum;

    private String idFrontImg;

    private String idBackImg;

    private String avatar;

    private SchoolDTOFull school;

    @JsonProperty("hometown")
    private DistrictDTOShort district;

//    private Collection<BookingDTO> bookings;
//    private Collection<DealDTO> deals;
//    private Collection<ContractDTO> contracts;
//    private Collection<RequestDTO> requests;
//    private Collection<ConversationDTO> conversations;
}
