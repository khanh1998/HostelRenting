
package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOCreate;

import java.util.Collection;

@Getter
@Setter
public class HGScheduleDTO {
    private Collection<ScheduleDTO> schedules;
    private GroupDTOCreate hGroup;;
}
