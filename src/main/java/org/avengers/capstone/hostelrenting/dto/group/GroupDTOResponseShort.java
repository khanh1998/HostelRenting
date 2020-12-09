package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import java.util.UUID;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 09:02
 * @project youthhostelapp
 */
@Getter @Setter
public class GroupDTOResponseShort {
    private Integer groupId;
    private UUID vendorId;
    private String groupName;
    private String buildingNo;
    @JsonUnwrapped
    @JsonIgnoreProperties("displayOrder")
    private CategoryDTO category;
    private String imgUrl;
    @JsonProperty("address")
    private AddressFull addressFull;
    private String appendixContract;
}
