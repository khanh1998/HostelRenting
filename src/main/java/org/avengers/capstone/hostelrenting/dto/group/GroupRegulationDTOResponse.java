package org.avengers.capstone.hostelrenting.dto.group;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.io.Serializable;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 15:30
 * @project youthhostelapp
 */
@Getter
@Setter
public class GroupRegulationDTOResponse implements Serializable {
    private int regulationId;
    private String regulationName;
    private float finePayment;
    private boolean isAllowed;
    private boolean isActive;
}
