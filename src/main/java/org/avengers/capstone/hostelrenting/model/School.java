package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schoolId;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Renter> renters;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
