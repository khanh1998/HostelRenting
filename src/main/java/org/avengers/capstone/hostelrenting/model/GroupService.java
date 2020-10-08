package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "group_service")
public class GroupService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serDetailId;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false, columnDefinition = "varchar(10) default 'nghìn'")
    private String priceUnit;

    @Column(nullable = false, columnDefinition = "varchar(10) default 'tháng'")
    private String timeUnit;

    @Column(nullable = false, columnDefinition = "varchar(10) default 'phòng'")
    private String userUnit;

    @Column(nullable = false, columnDefinition = "bool default true")
    private boolean isRequired;

    @Column(nullable = false, columnDefinition = "bool default true")
    private boolean isActive;

    @Column(nullable = false)
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToMany(mappedBy = "serDetails", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Contract> contracts;
}