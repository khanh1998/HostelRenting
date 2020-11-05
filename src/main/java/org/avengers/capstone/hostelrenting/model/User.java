package org.avengers.capstone.hostelrenting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String avatar;

    @Column(nullable = false)
    private String firebaseToken;

    private Integer yearOfBirth;

    private String idIssuedLocation;

    private Long idIssuedDate;

    private String householdAddress;

    private String currentAddress;

    private String citizenIdNum;

    private String citizenIdFrontImg;

    private String citizenIdBackImg;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private boolean isBlocked;

    @Column(columnDefinition = "boolean default false")
    private boolean isCensored;

    /**
     * creating timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    /**
     * updating timestamp
     */
    @Column(name = "updated_at")
    private Long updatedAt;
}
