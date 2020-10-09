package org.avengers.capstone.hostelrenting.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO implements Serializable {
    private int scheduleId;
    private String dayOfWeek;
    private Schedule.CODE code;
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
