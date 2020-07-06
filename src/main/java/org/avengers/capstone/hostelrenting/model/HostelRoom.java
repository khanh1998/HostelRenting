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
@Table(name = "hostelRoom")
public class HostelRoom implements Serializable {
    @Id
    @Column(name = "hostel_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "room_name", nullable = false)
    private String categoryName;

    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "hostel_type_id")
    private HostelType hostelType;
}
