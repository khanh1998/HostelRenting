package org.avengers.capstone.hostelrenting.dto.groupService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 13:12
 * @project youthhostelapp
 */
public class GroupServiceDTOCreate extends GroupServiceDTOCreateForGroup {

    @Getter
    @Setter
    private String serviceName;
}
