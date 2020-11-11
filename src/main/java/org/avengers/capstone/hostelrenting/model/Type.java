package org.avengers.capstone.hostelrenting.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Comparator;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "type_hostel")
public class Type {
    public enum STATUS{AVAILABLE, ONLY_EXTENSION, NOT_AVAILABLE}

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    private float score;

    @Column(name = "title", nullable = false, length = 100)
    @NotBlank(message = "Title is mandatory")
    private String title;

    // unit: milion VND
    @Column(nullable = false)
    private float price;

    @Column(nullable = false, columnDefinition = "varchar(10) default 'triệu'")
    private String priceUnit;

    @Column(nullable = false)
    private float superficiality;

    @Column(nullable = false)
    private int capacity;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    // unit: month
    @Column(nullable = false)
    private int depositTime;

    @Column(columnDefinition = "varchar(20) default 'tháng'")
    private String depositTimeUnit;

    @Transient
    private int schoolmate;

    @Transient
    private int compatriot;

    @Transient
    private int availableRoom;

    @Transient
    private int currentBooking;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(columnDefinition = "varchar(20) default 'AVAILABLE'")
    @Enumerated(EnumType.STRING)
    private STATUS status;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Room> rooms;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TypeImage> typeImages;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<Deal> deals;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<Feedback> feedbacks;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "type_facility",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Collection<Facility> facilities;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
