package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostelgroup_schedule")
public class HostelGroupSchedule implements Serializable {
    @Id
    @Column(name = "hostelgroup_schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hostelGroupScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="hostelGroupId")
    @JsonIgnore
    private HostelGroup hostelGroup;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name="scheduleId")
    @JsonIgnore
    private Schedule schedule;
}
