package org.avengers.capstone.hostelrenting.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GroupScheduleDTOResponseV2 implements Serializable {
    private int id;
    private int scheduleId;
    @JsonUnwrapped
    @JsonIgnoreProperties(value = "scheduleId")
    private ScheduleDTO schedule;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    private List<ScheduleDTOV2> timeRanges;
}
