package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class UtilityDTO implements Serializable {
    private int id;
    private String name;
    private String longitude;
    private String latitude;
}
