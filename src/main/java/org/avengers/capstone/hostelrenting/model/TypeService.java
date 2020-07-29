package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "type_service")
public class TypeService {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private HostelType hostelType;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private Double servicePrice;

    private String priceUnit;

    private String userUnit;

    private String timeUnit;
}
