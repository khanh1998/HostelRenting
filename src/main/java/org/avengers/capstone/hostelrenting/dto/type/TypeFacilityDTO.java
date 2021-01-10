package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.avengers.capstone.hostelrenting.model.Facility;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
public class TypeFacilityDTO implements Serializable {
    private int id;
    private int quantity;
    private String unit;
    @JsonUnwrapped
    private Facility facility;
}
