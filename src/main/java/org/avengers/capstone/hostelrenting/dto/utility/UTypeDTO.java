package org.avengers.capstone.hostelrenting.dto.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.utility.UtilityDTO;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UTypeDTO implements Serializable {
    @JsonProperty("typeId")
    private int uTypeId;
    private String name;
    private String imageUrl;
    private List<UtilityDTO> utilities;
}
