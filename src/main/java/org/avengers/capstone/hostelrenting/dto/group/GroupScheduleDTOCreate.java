package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.avengers.capstone.hostelrenting.dto.schedule.ScheduleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duattt on 10/28/20
 * @created 28/10/2020 - 06:55
 * @project youthhostelapp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupScheduleDTOCreate implements Serializable {
    private Integer scheduleId;
    private List<String> timeRange;
}
