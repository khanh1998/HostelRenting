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
    @NotBlank(message = "Username is mandatory")
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String avatar;

    private String firebaseToken;

    private Long yearOfBirth;

    private String idIssuedLocation;

    private Long idIssuedDate;

    private String householdAddress;

    private String currentAddress;

    private String idNum;

    private String idFrontImg;

    private String idBackImg;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private boolean isBlocked;

    @Column(columnDefinition = "boolean default false")
    private boolean isCensored;
}
