package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "type_image")
public class TypeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(nullable = false)
    @NotBlank(message = "Image resource is mandatory")
    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private HostelType hostelType;
}
