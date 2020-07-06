package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeStatusDTO implements Serializable {
    private int statusId;
    private String statusName;
}
