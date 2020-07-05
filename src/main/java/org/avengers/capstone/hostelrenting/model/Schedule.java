package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scheduleId;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    @JsonBackReference
//    private Vendor vendor;

//    @ManyToMany(mappedBy = "schedules", fetch = FetchType.LAZY)
//    private List<HostelGroup> hostelGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnoreProperties
    private List<HostelGroupSchedule> hostelGroupSchedules;
}
