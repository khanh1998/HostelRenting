package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostelgroup")
public class HostelGroup implements Serializable {
    @Id
    @Column(name = "hostel_group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hostelGroupId;

    @Column(name = "hostel_group_name", nullable = false)
    private String hostelGroupName;

    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;

    private String longitude;

    private String latitude;

//    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL)
//    private List<HostelType> hostelTypes;
//
//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendor;

//    @ManyToOne
//    @JoinColumn(name = "province_id")
//    private Province province;
//
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

//    @ManyToMany
//    @JoinTable(name = "hostelgroup_schedule", joinColumns = @JoinColumn(name = "hostel_group_id"), inverseJoinColumns = @JoinColumn(name = "schedule_id"))
//    private List<Schedule> schedules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hostelGroup", cascade = CascadeType.ALL)
    @JsonIgnoreProperties
    private List<HostelGroupSchedule> hostelGroupSchedules;
}
