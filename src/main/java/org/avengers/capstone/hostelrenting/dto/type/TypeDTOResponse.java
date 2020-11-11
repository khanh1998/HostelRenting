package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;
import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTOResponse;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOCreate;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOResponse;

import java.util.Collection;

@Getter
@Setter
public class TypeDTOResponse extends TypeDTO {

    @Getter
    @Setter
    private Collection<RoomDTOResponse> rooms;

    @Getter
    @Setter
    private Collection<FacilityDTOResponse> facilities;

    @Getter
    @Setter
    @JsonProperty(value = "imageUrls")
    private Collection<ImageDTOResponse> typeImages;
}
