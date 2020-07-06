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
@Table(name = "hosteltype")
public class HostelType implements Serializable {
    @Id
    @Column(name = "hostel_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostelTypeId;

    @Column(name = "hostel_type_name", nullable = false)
    private String hostelTypeName;

    private String price;

    private float superficiality;

    private int capacity;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TypeStatus typeStatus;

    @ManyToOne
    @JoinColumn(name = "hostel_group_id")
    private HostelGroup hostelGroup;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "hostelType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HostelImage> hostelImages;

    @OneToMany(mappedBy = "hostelType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HostelRoom> hostelRooms;
}
