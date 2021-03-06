package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(nullable = false)
    @NotBlank(message = "Service name is mandatory")
    private String serviceName;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Collection<GroupService> groupServices;
}
