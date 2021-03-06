package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "group_schedule")
public class GroupSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(nullable = false)
    @NotBlank(message = "Start time is mandatory")
    private String startTime;

    @Column(nullable = false)
    @NotBlank(message = "End time is mandatory")
    private String endTime;

    @Transient
    private String timeRange;

    public String getTimeRange() {
        return startTime + " - " + endTime;
    }
}
