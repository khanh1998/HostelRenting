package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:07
 * @project youthhostelapp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @Column(length = 50)
    private String managerName;

    @Column(unique = true, length = 10)
    private String managerPhone;

}
