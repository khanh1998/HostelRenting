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
@Table(name = "typeStatus")
public class TypeStatus implements Serializable {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int statusId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @OneToMany(mappedBy = "typeStatus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HostelType> hostelTypes;
}
