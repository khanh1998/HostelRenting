package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "room_hostel")
public class Room {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @Column(name = "room_name", nullable = false)
    @NotBlank(message = "Name is mandatory for hostel room")
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @Column(name = "is_available", nullable = false, columnDefinition = "boolean default true")
    private boolean isAvailable;

//    @OneToMany(mappedBy = "hostelRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Contract> contracts;
}