package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 09:02
 * @project youthhostelapp
 */
@Getter @Setter
public class GroupDTOResponseShort {
    private Integer groupId;
    private Long vendorId;
    private String groupName;
    private String buildingNo;
    private String imgUrl;
    @JsonProperty("address")
    private AddressFull addressFull;
}
