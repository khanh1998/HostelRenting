package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
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
