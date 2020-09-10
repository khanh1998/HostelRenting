package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceDTO implements Serializable {
    private int serviceId;
    private String serviceName;
}
