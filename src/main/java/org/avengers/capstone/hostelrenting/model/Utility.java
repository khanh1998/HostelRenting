package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "utility")
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int utilityId;

    @Column(length = 100)
    private String name;

    private Double longitude;

    private Double latitude;

    @Transient
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "u_type_id", nullable = false)
    private UType uType;
}
