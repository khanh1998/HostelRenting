package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "schedule")
public class Schedule {
    public enum CODE{MON, TUE, WED, THU, FRI, SAT, SUN}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CODE code;

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
