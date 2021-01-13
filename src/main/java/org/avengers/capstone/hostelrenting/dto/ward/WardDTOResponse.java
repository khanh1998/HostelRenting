package org.avengers.capstone.hostelrenting.dto.ward;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.street.StreetDTO;
import org.avengers.capstone.hostelrenting.dto.StreetWardDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class WardDTOResponse extends WardDTO {
    @JsonProperty("streets")
    private List<StreetWardDTO> streetWards;

    public List<StreetDTO> getStreetWards() {
        if (streetWards != null)
            return streetWards
                    .stream()
                    .map(StreetWardDTO::getStreet)
                    .collect(Collectors.toList());
        return null;
    }
}
