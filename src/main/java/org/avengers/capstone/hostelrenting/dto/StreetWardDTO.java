package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTO;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StreetWardDTO implements Serializable {
    private Integer streetWardId;
    private StreetDTO street;
    private WardDTO ward;

}
