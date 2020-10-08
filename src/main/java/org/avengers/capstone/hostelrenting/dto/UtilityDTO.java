package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UtilityDTO implements Serializable {
    private int id;
    private String name;
    private String longitude;
    private String latitude;
    private double distance;
}
