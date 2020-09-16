package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilityDTO implements Serializable {
    private int id;
    private String name;
    private String longitude;
    private String latitude;
    private double distance;
}
