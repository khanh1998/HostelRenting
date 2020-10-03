package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TypeStatusDTO implements Serializable {
    private int statusId;
    private String statusName;
}
