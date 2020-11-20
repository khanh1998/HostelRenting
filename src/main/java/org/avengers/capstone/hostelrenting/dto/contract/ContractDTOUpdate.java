package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOCreate;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.model.Room;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author duattt on 11/8/20
 * @created 08/11/2020 - 10:28
 * @project youthhostelapp
 */
public class ContractDTOUpdate implements Serializable {

    public ContractDTOUpdate() {
        this.status = Contract.STATUS.INACTIVE;
        this.updatedAt = System.currentTimeMillis();
    }
    @Getter
    @Setter
    @JsonIgnore
    private Integer contractId;

    @Getter
    @Setter
    private String appendixContract;

    @Getter @Setter
    @NotNull(message = "Room id is mandatory!")
    private Integer roomId;

    @Getter @Setter
    @JsonIgnore
    private Room room;

    @Getter @Setter
//    @NotNull(message = "Start time is mandatory!")
    private Long startTime;

    @Getter @Setter
    @NotNull(message = "Contract duration is mandatory!")
    private Integer duration;

    @JsonIgnore
    @Getter
    private Long updatedAt;

    @Getter
    @JsonIgnore
    private Contract.STATUS status;

    @Getter
    @Setter
    private boolean isPaid;

    @Getter @Setter
    @JsonProperty(value = "groupServiceIds")
    private Set<GroupServiceDTOForContract> groupServiceIds;

    @Getter
    @Setter
    @JsonProperty(value = "images")
    private Set<ImageDTOCreate> contractImages;

    @Getter @Setter
    @JsonIgnore
    private Set<GroupService> groupServices;

}
