package org.avengers.capstone.hostelrenting.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor

@MappedSuperclass
public class User {
    public enum ROLE{RENTER, VENDOR, ADMIN, MANAGER}

    public User() {
        this.userId = UUID.randomUUID();
    }

    @Id
    private UUID userId;

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

    private String idIssuedLocation;

    private Long idIssuedDate;

    private String householdAddress;

    private String currentAddress;

    @Column(length = 30)
    private String citizenIdNum;

    private String citizenIdFrontImg;

    private String citizenIdBackImg;

    @Transient
    private ROLE role;

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
