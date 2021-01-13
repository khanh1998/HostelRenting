package org.avengers.capstone.hostelrenting.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "admin")
public class Admin {
    public Admin(UUID userId) {
        this.userId = UUID.randomUUID();
    }

    @Id
    private UUID userId;

    private String password;

    @Column(unique = true, length = 10)
    private String phone;

    @Transient
    private User.ROLE role;

    public Admin() {

    }

    public User.ROLE getRole() {
        return User.ROLE.ADMIN;
    }

}
