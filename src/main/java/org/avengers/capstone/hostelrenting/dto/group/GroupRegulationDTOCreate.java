package org.avengers.capstone.hostelrenting.dto.group;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 14:58
 * @project youthhostelapp
 */
@Getter
@Setter
public class GroupRegulationDTOCreate implements Serializable {
    private int regulationId;
    private float finePayment;
    private boolean isAllowed;
    private boolean isActive;
}
