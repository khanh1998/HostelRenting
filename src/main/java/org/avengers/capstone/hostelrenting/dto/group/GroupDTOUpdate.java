package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 10/9/20
 * @created 09/10/2020 - 20:26
 * @project youthhostelapp
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDTOUpdate {
    public GroupDTOUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
    private String groupName;
    private String curfewTime;
    private Boolean ownerJoin;
    private String imgUrl;
    private Float downPayment;

    @Getter
    @JsonIgnore
    private Long updatedAt;
}
