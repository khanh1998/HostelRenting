package org.avengers.capstone.hostelrenting.dto;


import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Schedule;

@Data
public class ScheduleDTO {
    private int scheduleId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private Schedule.CODE code;
}
