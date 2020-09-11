package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "hg_schedule")
public class HGSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hgroupScheduleId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private HostelGroup hGroup;

    @Column(nullable = false)
    @NotBlank(message = "Start time is mandatory")
    private String startTime;

    @Column(nullable = false)
    @NotBlank(message = "End time is mandatory")
    private String endTime;
}
