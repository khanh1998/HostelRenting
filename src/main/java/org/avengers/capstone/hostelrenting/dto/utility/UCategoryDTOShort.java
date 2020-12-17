package org.avengers.capstone.hostelrenting.dto.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author duattt on 16/12/2020
 * @created 16/12/2020 - 11:49
 * @project youthhostelapp
 */
@Getter
@Setter
@NoArgsConstructor
public class UCategoryDTOShort {
    @JsonProperty("categoryId")
    private int uCategoryId;
    private String name;
    private String code;
    private int displayOrder;
}
