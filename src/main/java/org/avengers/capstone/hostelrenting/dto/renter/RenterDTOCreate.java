package org.avengers.capstone.hostelrenting.dto.renter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.SchoolDTOFull;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.user.UserDTORegister;

import javax.validation.Valid;

/**
 * @author duattt on 9/29/20
 * @created 29/09/2020 - 09:46
 * @project youthhostelapp
 */
public class RenterDTOCreate extends UserDTORegister {
    @Getter
    @Setter
    @JsonProperty("hometown")
    @JsonIgnoreProperties({"provinceName"})
    private @Valid ProvinceDTO province;

    @Getter
    @Setter
    @JsonIgnoreProperties({"schoolName", "longitude", "latitude", "district"})
    private @Valid SchoolDTOFull school;
}
