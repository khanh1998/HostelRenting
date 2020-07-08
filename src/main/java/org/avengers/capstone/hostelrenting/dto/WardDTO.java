package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WardDTO implements Serializable {
    private int wardId;
    private String wardName;
    private Integer districtId;
}
