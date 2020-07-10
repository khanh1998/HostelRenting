package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

@Data
public class ServiceDTO {
    private int serviceId;
    private String serviceName;
    private long servicePrice;
    private String unit;
}
