package org.avengers.capstone.hostelrenting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Renter {
    @Id
    private String username;
    private String name;
}
