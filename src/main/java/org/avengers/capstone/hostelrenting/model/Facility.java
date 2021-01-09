package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facilityId;

    @Column(nullable = false, unique = true, length = 30)
    @NotBlank(message = "Facility name is mandatory")
    private String facilityName;

    @Column(columnDefinition = "boolean default false",nullable = false)
    private boolean isApproved;

    @JsonIgnore
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<TypeFacility> typeFacilities;
}
