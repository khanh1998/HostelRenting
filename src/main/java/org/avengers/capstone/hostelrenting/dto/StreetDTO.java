package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOShort;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class StreetDTO implements Serializable {
    private Integer streetId;
    private String streetName;
}
