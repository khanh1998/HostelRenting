package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
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
    @Column(unique = true)
    private CODE code;

    @Column(nullable = false)
    @NotBlank(message = "Day of week time is mandatory")
    private String dayOfWeek;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GroupSchedule> groupSchedules;

}
