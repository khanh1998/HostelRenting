package org.avengers.capstone.hostelrenting.dto.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author duattt on 10/8/20
 * @created 08/10/2020 - 12:15
 * @project youthhostelapp
 */
@Getter
@Setter
public class GroupServiceDTOCreate implements Serializable {
    private Integer groupServiceId;
    private Integer serviceId;
    private Float price;
    private String userUnit;
    @NotNull(message = "Price unit is mandatory")
    private String priceUnit;
    @NotNull(message = "Time unit is mandatory")
    private String timeUnit;
    private Boolean isRequired;
    private Boolean isActive;
    private Long createdAt;
}
