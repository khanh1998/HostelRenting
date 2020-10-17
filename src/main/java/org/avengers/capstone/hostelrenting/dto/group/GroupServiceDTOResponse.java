package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author duattt on 10/8/20
 * @created 08/10/2020 - 12:21
 * @project youthhostelapp
 */
@Setter
@Getter
public class GroupServiceDTOResponse implements Serializable {
    private int serviceId;
    private int groupServiceId;
    private String serviceName;
    private float price;
    private String priceUnit;
    private String timeUnit;
    private String userUnit;
    private boolean isRequired;
    private boolean isActive;
    private long createdAt;

    @JsonIgnore
    public Boolean isActive() {
        return isActive;
    }
}
