package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceDetailDTO implements Serializable {
    private Integer serDetailId;
    private Integer serviceId;
    private Float price;
    private String priceUnit;
    private String timeUnit;
    private String userUnit;
    private Boolean isRequired;
    private Boolean isActive;
    private Long createdAt;
}
