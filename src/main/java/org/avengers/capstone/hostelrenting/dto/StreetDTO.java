package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StreetDTO implements Serializable {
    private Integer streetId;
    private String streetName;
    private Integer wardId;
}
