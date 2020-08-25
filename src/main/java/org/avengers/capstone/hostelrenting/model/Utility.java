package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "utility")
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int utilityId;

    private String name;

    private String longitude;

    private String latitude;

    @ManyToOne
    @JoinColumn(name = "u_type_id", nullable = false)
    private UType uType;
}
