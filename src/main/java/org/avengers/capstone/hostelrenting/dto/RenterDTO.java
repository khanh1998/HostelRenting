package org.avengers.capstone.hostelrenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RenterDTO {
    private String username;
    private String name;
    private String phone;
    private String email;
}
