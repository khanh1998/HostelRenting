package org.avengers.capstone.hostelrenting.dto.renter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.SchoolDTOFull;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOResponse;

import java.io.Serializable;

@Getter
@Setter
public class RenterDTOResponse extends UserDTOResponse implements Serializable  {

    private SchoolDTOFull school;

    @JsonProperty("hometown")
    private ProvinceDTO province;

    private boolean isBlocked;
    private boolean isCensored;
}
