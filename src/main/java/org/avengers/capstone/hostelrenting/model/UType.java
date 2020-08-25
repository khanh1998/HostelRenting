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
@Table(name = "u_type")
public class UType{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uTypeId;

    private String name;

    private String imageUrl;

    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "u_category_id", nullable = false)
    private UCategory uCategory;

    @OneToMany(mappedBy = "uType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Utility> utilities;
}
