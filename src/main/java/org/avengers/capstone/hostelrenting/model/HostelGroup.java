package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostel_group")
public class HostelGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "building_no", nullable = false)
    private String buildingNo;

    @NotBlank(message = "Longitude is mandatory")
    private Double longitude;

    @NotBlank(message = "Latitude is mandatory")
    private Double latitude;

    private String description;

    private boolean ownerJoin;

    private String curfewTime;

    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelType> hostelTypes;

    @ManyToOne
    @JoinColumn(name = "street_id", nullable = false)
    private Street street;

    @ManyToOne
    @JoinColumn(name="vendor_id", nullable = false)
    private Vendor vendor;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @JoinTable(name = "group_service",
//            joinColumns = @JoinColumn(name = "group_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id")
//    )
//    private Set<Service> services;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "group_schedule",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedules;
}
