package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class UCategoryDTO implements Serializable {
    @JsonProperty("categoryId")
    private int uCategoryId;
    private String name;
    private String code;
    @JsonProperty("utilityTypes")
    private List<UTypeDTO> uTypes;
    private int displayOrder;
}
