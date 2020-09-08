package org.avengers.capstone.hostelrenting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "sample")
@TypeDef(
        name = "list-array",
        typeClass = ArrayList.class
)
public class SampleHostels {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Float superficiality;

    private Float price;

    private Double longitude;

    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "street_ward_id")
    private StreetWard address;

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
}
