package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "hostel_type")
public class HostelType {
    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private String priceUnit;

    @Column(nullable = false)
    private float superficiality;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int view;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private HostelGroup hostelGroup;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TypeStatus typeStatus;

    @OneToMany(mappedBy = "hostelType", fetch = FetchType.LAZY)
    private Collection<HostelRoom> hostelRooms;

    @OneToMany(mappedBy = "hostelType", fetch = FetchType.LAZY)
    private Collection<TypeImage> typeImages;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "type_facility",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<Facility> facilities;

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "type_service",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

}
