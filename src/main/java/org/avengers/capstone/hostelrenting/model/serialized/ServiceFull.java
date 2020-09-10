package org.avengers.capstone.hostelrenting.model.serialized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFull {
    private int serviceId;
    private String serviceName;
    private float price;
    private String priceUnit;
    private String timeUnit;
    private String userUnit;
    private Boolean isRequired;
    private long createdAt;
}
