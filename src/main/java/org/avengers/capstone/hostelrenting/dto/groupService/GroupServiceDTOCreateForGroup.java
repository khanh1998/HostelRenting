package org.avengers.capstone.hostelrenting.dto.groupService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author duattt on 10/8/20
 * @created 08/10/2020 - 12:15
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class GroupServiceDTOCreateForGroup implements Serializable {

    public GroupServiceDTOCreateForGroup() {
        this.createdAt = System.currentTimeMillis();
    }

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

    @JsonIgnore
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public void setGroupServiceId(Integer groupServiceId) {
        this.groupServiceId = groupServiceId;
    }
}
