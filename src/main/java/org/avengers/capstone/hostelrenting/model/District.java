package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "district")
public class District implements Serializable {
    @Id
    @Column(name = "district_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HostelGroup> hostelGroups;
}
