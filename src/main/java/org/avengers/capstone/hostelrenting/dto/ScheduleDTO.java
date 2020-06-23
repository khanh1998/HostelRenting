package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleDTO implements Serializable {
    private int scheduleId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private int vendorId;
}
