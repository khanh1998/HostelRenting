package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseV3;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOResponse;

import java.util.Collection;

@Getter
@Setter
public class TypeDTOResponseV2 extends TypeDTO {

    @Override
    @JsonIgnore
    public boolean isDeleted() {
        return super.isDeleted();
    }

    @Getter
    @Setter
    private Collection<RoomDTOResponse> rooms;

    @Getter
    @Setter
    private Collection<TypeFacilityDTO> facilities;

    @Getter
    @Setter
    @JsonProperty(value = "imageUrls")
    private Collection<ImageDTOResponse> typeImages;

    @JsonProperty(value = "group")
    private GroupDTOResponseV3 group;
}
