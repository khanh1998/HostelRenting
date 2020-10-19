package org.avengers.capstone.hostelrenting.dto.group;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.avengers.capstone.hostelrenting.dto.schedule.ScheduleDTO;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GroupScheduleDTOResponse implements Serializable {
    private int scheduleId;
    //    private String dayOfWeek;
//    private Schedule.CODE code;
    @JsonUnwrapped
    private ScheduleDTO schedule;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    private List<String> timeRange;

    public List<String> getTimeRange() {
        if (timeRange == null)
            timeRange = new ArrayList<>();
        return timeRange;
    }
}
