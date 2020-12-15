package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author duattt on 01/12/2020
 * @created 01/12/2020 - 12:07
 * @project youthhostelapp
 */

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "manager")
public class Manager {

    public Manager(){
        this.managerId = UUID.randomUUID();
    }

    @Id
    private UUID managerId;

    @Column(length = 50)
    private String managerName;

    @Column(unique = true, length = 10)
    private String managerPhone;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean isActive;
}
