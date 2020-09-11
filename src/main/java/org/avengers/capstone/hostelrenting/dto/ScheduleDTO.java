package org.avengers.capstone.hostelrenting.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
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
        timeRange.add(startTime + " - " + endTime);
        return timeRange;
    }
}
