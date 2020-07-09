package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "hostel_type")
public class HostelType {
    @Id
    @Column(name = "hostel_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostelTypeId;

    @ManyToOne
    @JoinColumn(name = "hostel_group_id")
    private HostelGroup hostelGroup;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_status_id")
    private TypeStatus typeStatus;

    @Column(name = "type_name", nullable = false)
    @NotBlank(message = "Type name is mandatory")
    private String hostelTypeName;

    @Column(nullable = false)
    @NotNull(message = "Price is mandatory")
    private long price;

    @Column(nullable = false)
    @NotNull(message = "Superficiality is mandatory")
    private float superficiality;

    @Column(nullable = false)
    @NotNull(message = "Capacity is mandatory")
    private int capacity;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;
}
