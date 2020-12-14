package org.avengers.capstone.hostelrenting.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(0)
    @Max(10)
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
    @Min(5)
    @Max(200)
    private float superficiality;

    @Column(nullable = false)
    private int capacity;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    // unit: month
    @Column(nullable = false)
    private int deposit;

    private String description;

    @Transient
    private int schoolmate;

    @Transient
    private int compatriot;

    @Transient
    private int availableRoom;

    @Transient
    private int currentBooking;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(columnDefinition = "varchar(20) default 'AVAILABLE'", nullable = false)
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

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TypeFacility> typeFacilities;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
