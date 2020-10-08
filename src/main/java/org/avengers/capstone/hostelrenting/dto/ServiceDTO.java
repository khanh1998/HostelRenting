package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceDTO implements Serializable {
    private int serviceId;
    private String serviceName;
}
