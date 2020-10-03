
package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOShort;

import java.util.Collection;

@Getter
@Setter
public class HGScheduleDTO {
    private Collection<ScheduleDTO> schedules;
    private HostelGroupDTOShort hGroup;;
}
