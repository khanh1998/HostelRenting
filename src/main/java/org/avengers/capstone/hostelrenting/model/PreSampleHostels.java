package org.avengers.capstone.hostelrenting.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * @author duattt on 10/5/20
 * @created 05/10/2020 - 14:01
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "samplePre")
@TypeDef(
        name = "list-array",
        typeClass = ArrayList.class
)
public class PreSampleHostels {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Float superficiality;

    private Float price;

    private Double longitude;

    private Double latitude;

    private Integer streetWardId;

    @Type(type = "list-array")
    @Column(
            name = "facility_ids",
            columnDefinition = "integer[]"
    )
    private Integer[] facilityIds;

    @Type(type = "list-array")
    @Column(
            name = "service_ids",
            columnDefinition = "integer[]"
    )
    private Integer[] services;

    private Integer categoryId;

    private Long postAt;
}
