package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceDetailDTO implements Serializable {
    private Integer serDetailId;
    private Float price;
    private String priceUnit;
    private String timeUnit;
    private String userUnit;
    private Boolean isRequired;
    private Boolean isActive;
    private Long createdAt;
}
