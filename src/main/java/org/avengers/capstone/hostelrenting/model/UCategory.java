package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "u_category")
public class UCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uCategoryId;

    @Column(length = 30)
    private String name;


    @Column(length = 15)
    private String code;

    private Integer displayOrder;

    @OneToMany(mappedBy = "uCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UType> uTypes;
}
