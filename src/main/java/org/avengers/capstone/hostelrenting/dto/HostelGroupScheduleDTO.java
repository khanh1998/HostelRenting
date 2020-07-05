package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelGroupScheduleDTO implements Serializable {
    private int scheduleId;
    private int hostelGroupId;
}
