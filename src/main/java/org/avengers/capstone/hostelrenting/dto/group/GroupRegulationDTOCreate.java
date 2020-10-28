package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 14:58
 * @project youthhostelapp
 */
@Getter
@Setter
public class GroupRegulationDTOCreate implements Serializable {
    public GroupRegulationDTOCreate() {
        this.isActive = true;
    }

    private Integer regulationId;
    private Boolean isAllowed;
    @JsonIgnore
    @Getter
    private Boolean isActive;
}
