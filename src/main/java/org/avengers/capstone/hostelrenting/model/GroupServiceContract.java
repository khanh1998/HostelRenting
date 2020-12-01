package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:19
 * @project youthhostelapp
 */
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder

//@Entity
//@Table(name = "group_service_contract")
public class GroupServiceContract {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "group_service_id", nullable = false)
    private GroupService groupService;
}
