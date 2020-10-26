package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "type_status")
public class TypeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Type status name is mandatory")
    private String statusName;

    @OneToMany(mappedBy = "typeStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Type> types;
}
