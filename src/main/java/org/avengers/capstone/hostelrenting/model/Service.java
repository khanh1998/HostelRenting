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
    private String serviceName;

    @Column(columnDefinition = "boolean default false",nullable = false)
    private boolean isApproved;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Collection<GroupService> groupServices;
}
