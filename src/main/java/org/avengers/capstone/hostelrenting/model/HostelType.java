package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "hostel_type")
public class HostelType {
    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private HostelGroup hostelGroup;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "type_status_id")
    private TypeStatus typeStatus;

    @Column(name = "type_name", nullable = false)
    @NotBlank(message = "Type name is mandatory")
    private String typeName;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private float superficiality;

    @Column(nullable = false)
    private int capacity;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "hostelType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelRoom> hostelRooms;
}
