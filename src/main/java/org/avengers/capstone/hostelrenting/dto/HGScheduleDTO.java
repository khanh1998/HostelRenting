
package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOShort;

import java.util.Collection;

@Data
public class HGScheduleDTO {
    private Collection<ScheduleDTO> schedules;
    private HostelGroupDTOShort hGroup;;
}
