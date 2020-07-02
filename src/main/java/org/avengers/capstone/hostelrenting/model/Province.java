package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "province")
public class Province implements Serializable {
    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int provinceId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Province name is mandatory")
    private String provinceName;

    @JsonIgnore
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

//    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
//    private List<HostelGroup> hostelGroups;
}
