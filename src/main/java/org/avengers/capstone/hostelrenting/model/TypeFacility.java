package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
@ToString

@Entity
@Table(name = "type_facility")
public class TypeFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity", columnDefinition = "int default 1")
    private int quantity;

    @Column(name = "unit", columnDefinition = "varchar(10) default 'cái'")
    private String unit;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
}
