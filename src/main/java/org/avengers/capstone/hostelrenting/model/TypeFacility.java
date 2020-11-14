package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 10:18
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "type_facility")
public class TypeFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
}
