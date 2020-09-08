package org.avengers.capstone.hostelrenting.dto.ward;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.StreetDTO;
import org.avengers.capstone.hostelrenting.dto.StreetWardDTO;
import org.avengers.capstone.hostelrenting.model.Street;
import org.hibernate.mapping.Collection;
import org.modelmapper.Converters;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class WardDTOFull extends WardDTOShort {
    @JsonProperty("streets")
    private List<StreetWardDTO> streetWards;

    public List<StreetDTO> getStreetWards() {
        return streetWards
                .stream()
                .map(StreetWardDTO::getStreet)
                .collect(Collectors.toList());
    }
}
