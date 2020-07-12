package org.avengers.capstone.hostelrenting.dto;


import lombok.Data;

@Data
public class ScheduleDTO {
    private int scheduleId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
}
