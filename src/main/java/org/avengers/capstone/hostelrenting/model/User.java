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

    @Column(nullable = false, length = 50)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 10)
    private String phone;

    private String avatar;

    private String firebaseToken;

    private Integer yearOfBirth;

    @Column(length = 100)
    private String idIssuedLocation;

    private Long idIssuedDate;

    @Column(length = 150)
    private String householdAddress;

    @Column(length = 150)
    private String currentAddress;

    @Column(length = 30)
    private String citizenIdNum;

    private String citizenIdFrontImg;

    private String citizenIdBackImg;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isBlocked;

    @Column(columnDefinition = "boolean default false", nullable = false)
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
