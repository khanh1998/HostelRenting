package org.avengers.capstone.hostelrenting.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ward")
public class Ward {
    @Id
    @Column(name = "ward_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wardId;

    @Column(name = "ward_name", nullable = false, length = 50)
    @NotBlank(message = "Ward name is mandatory")
    private String wardName;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StreetWard> streetWards;

}
