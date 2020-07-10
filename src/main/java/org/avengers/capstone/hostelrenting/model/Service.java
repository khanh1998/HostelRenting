package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Service name is mandatory")
    private String serviceName;

    @Column(nullable = false)
    @NotNull(message = "Service price is mandatory")
    private Long servicePrice;

    @Column(nullable = false)
    @NotBlank(message = "Service unit is mandatory")
    private String unit;

    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HostelGroup> hostelGroups;
}
