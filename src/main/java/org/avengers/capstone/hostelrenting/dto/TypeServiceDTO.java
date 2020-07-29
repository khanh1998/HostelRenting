package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeServiceDTO implements Serializable {
    private Integer id;
    private Integer typeId;
    private ServiceDTO service;
    private Double servicePrice;
    private String priceUnit;
    private String userUnit;
    private String timeUnit;
}
