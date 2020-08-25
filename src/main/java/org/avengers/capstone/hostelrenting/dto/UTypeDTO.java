package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class UTypeDTO implements Serializable {
    @JsonProperty("typeId")
    private int uTypeId;
    private String name;
    private String imageUrl;
    private int displayOrder;
    private List<UtilityDTO> utilities;
}
