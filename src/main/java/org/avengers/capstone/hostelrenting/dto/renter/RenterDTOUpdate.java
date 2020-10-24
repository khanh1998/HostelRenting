package org.avengers.capstone.hostelrenting.dto.renter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.SchoolDTOFull;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author duattt on 10/23/20
 * @created 23/10/2020 - 15:08
 * @project youthhostelapp
 */
public class RenterDTOUpdate extends UserDTOUpdate {
    @Getter
    @Setter
//    @NotNull(message = "Hometown province id is mandatory!")
    @JsonProperty("hometown")
    private @Valid ProvinceDTOShort province;

    @Getter
    @Setter
//    @NotNull(message = "School id is mandatory!")
    private @Valid SchoolDTOFull school;
}
