package org.avengers.capstone.hostelrenting.dto.ward;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class WardDTO implements Serializable {
    private int wardId;
    private String wardName;
}
