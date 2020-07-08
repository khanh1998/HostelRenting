package org.avengers.capstone.hostelrenting.dto.schedule;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleNoIdDTO implements Serializable {
    private int scheduleId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
}
