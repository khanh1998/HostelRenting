package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.Schedule;

import java.io.Serializable;

@Data
public class HostelGroupScheduleFullDTO implements Serializable {
    private HostelGroup hostelGroup;
    private Schedule schedule;
}
