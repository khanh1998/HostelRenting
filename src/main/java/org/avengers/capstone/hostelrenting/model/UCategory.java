package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "u_category")
public class UCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uCategoryId;

    private String name;

    private String code;

    @OneToMany(mappedBy = "uCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UType> uTypes;
}
