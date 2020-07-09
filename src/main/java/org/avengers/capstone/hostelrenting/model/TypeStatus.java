package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "type_status")
public class TypeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeStatusId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Type status is mandatory")
    private String typeStatusName;

    @OneToMany(mappedBy = "typeStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelType> hostelTypes;
}
