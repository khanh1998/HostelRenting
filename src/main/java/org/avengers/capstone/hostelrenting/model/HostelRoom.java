package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.dto.ContractDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostel_room")
public class HostelRoom {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @Column(name = "room_name", nullable = false)
    @NotBlank(message = "Name is mandatory for hostel room")
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private HostelType hostelType;

    @Column(name = "is_available", nullable = false, columnDefinition = "boolean default true")
    private boolean isAvailable;

//    @JsonIgnore
//    @OneToMany(mappedBy = "hostelRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Contract> contracts;
}
