package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

@Data
public class ServiceDTO {
    private int serviceId;
    private String serviceName;
    private int servicePrice;
    private String priceUnit;
    private String userUnit;
    private String timeUnit;
}
