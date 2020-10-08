package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "u_type")
public class UType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uTypeId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "u_category_id", nullable = false)
    private UCategory uCategory;

    @OneToMany(mappedBy = "uType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Utility> utilities;
}
