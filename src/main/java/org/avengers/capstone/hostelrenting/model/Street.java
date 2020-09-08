package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "street")
public class Street {
    @Id
    @Column(name = "street_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int streetId;

    @Column(name = "street_name", nullable = false)
    @NotBlank(message = "Street name is mandatory")
    private String streetName;

    @OneToMany(mappedBy = "street", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StreetWard> streetWardList;
}
