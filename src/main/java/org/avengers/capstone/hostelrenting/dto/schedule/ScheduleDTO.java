package org.avengers.capstone.hostelrenting.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Schedule;

/**
 * @author duattt on 10/17/20
 * @created 17/10/2020 - 14:43
 * @project youthhostelapp
 */
@Getter
@Setter
public class ScheduleDTO {
    private Schedule.CODE code;
    private String dayOfWeek;

}
