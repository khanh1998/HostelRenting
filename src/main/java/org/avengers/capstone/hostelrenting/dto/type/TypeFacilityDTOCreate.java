package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 10:52
 * @project youthhostelapp
 */
@Getter
@Setter
public class TypeFacilityDTOCreate {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer typeId;
    private Integer facilityId;
}
