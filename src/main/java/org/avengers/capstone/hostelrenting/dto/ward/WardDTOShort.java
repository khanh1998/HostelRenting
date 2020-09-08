package org.avengers.capstone.hostelrenting.dto.ward;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WardDTOShort implements Serializable {
    private int wardId;
    private String wardName;
}
