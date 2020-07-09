package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facilityId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Facility name is mandatory")
    private String facilityName;

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private Collection<HTypeFacility> hTypeFacilities;
}
