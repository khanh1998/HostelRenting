package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author duattt on 10/9/20
 * @created 09/10/2020 - 20:26
 * @project youthhostelapp
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDTOUpdate {
    private Integer groupId;
    private String groupName;
    private String curfewTime;
    private Boolean ownerJoin;
    private String imgUrl;
    private String managerName;
    private String managerPhone;
    private Float downPayment;
}
