package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOShort;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class StreetDTO implements Serializable {
    private Integer streetId;
    private String streetName;
}
