package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;


    @OneToMany(mappedBy = "street", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SampleHostels> sampleHostels;
}
