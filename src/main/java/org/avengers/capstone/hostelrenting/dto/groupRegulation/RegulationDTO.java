package org.avengers.capstone.hostelrenting.dto.groupRegulation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 11:24
 * @project youthhostelapp
 */
@Getter
@Setter
public class RegulationDTO {
    private int regulationId;
    private String regulationName;
    private boolean isApproved;
}
