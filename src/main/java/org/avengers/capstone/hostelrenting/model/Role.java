package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "role")
public class Role {
    public enum CODE{RENTER, VENDOR}

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "role_name", nullable = false, length = 15)
    private String roleName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CODE code;

    private String description;
}
