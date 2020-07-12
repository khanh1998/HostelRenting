package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    @Column(nullable = false)
    @NotBlank(message = "Start time is mandatory")
    private String startTime;

    @Column(nullable = false)
    @NotBlank(message = "End time is mandatory")
    private String endTime;

    @Column(nullable = false)
    @NotBlank(message = "Day of week time is mandatory")
    private String dayOfWeek;

    @ManyToMany(mappedBy = "schedules", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HostelGroup> hostelGroups;
}
