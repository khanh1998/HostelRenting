package org.avengers.capstone.hostelrenting.dto.schedule;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ScheduleDTO implements Serializable {
    private int scheduleId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private List<Integer> hostelGroupIds;
}
