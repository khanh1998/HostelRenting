package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostelgroup")
public class HostelGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;

    @NotBlank(message = "Longitude is mandatory")
    private String longitude;

    @NotBlank(message = "Latitude is mandatory")
    private String latitude;

    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelType> hostelTypes;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    @ManyToOne
    @JoinColumn(name="vendor_id", nullable = false)
    private Vendor vendor;


}
