package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author duattt on 10/6/20
 * @created 06/10/2020 - 09:31
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "regulation")
public class Regulation {

    /**
     * regulation id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regulationId;

    /**
     * regulation name
     */
    @Column(nullable = false, length = 50)
    private String regulationName;

    @Column(columnDefinition = "boolean default false",nullable = false)
    private boolean isApproved;

    /**
     * list of group_schedules
     */
    @OneToMany(mappedBy = "regulation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GroupRegulation> groupRegulations;
}