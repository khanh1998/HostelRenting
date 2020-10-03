package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "statistic")
public class Statistic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float avgPrice;

    private Float avgSuperficiality;

    @Column(nullable = false, unique = true)
    private Integer streetWardId;

    private Long count;
}
