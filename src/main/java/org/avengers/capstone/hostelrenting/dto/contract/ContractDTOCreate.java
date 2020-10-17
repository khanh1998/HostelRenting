package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

public class ContractDTOCreate {

    public ContractDTOCreate() {
        this.qrCode = UUID.randomUUID();
        this.status = Contract.STATUS.INACTIVE;
        this.createdAt = System.currentTimeMillis();
    }
    @Getter @Setter
    private Integer contractId;

    @Getter @Setter
    @NotNull(message = "Room id is mandatory!")
    private Integer roomId;

    @Getter @Setter
    @NotNull(message = "Vendor id is mandatory!")
    private Long vendorId;

    @Getter @Setter
    @NotNull(message = "Renter id is mandatory!")
    private Long renterId;

    @Getter @Setter
    private Integer dealId;

    @Getter @Setter
    private Integer bookingId;

    @Getter @Setter
    @NotNull(message = "Start time is mandatory!")
    private Long startTime;

    @Getter @Setter
    @NotNull(message = "Contract duration is mandatory!")
    private float duration;

    @Getter @Setter
    private String evidenceImgUrl;

    @Getter
    private Long createdAt;

    @Getter
    private Contract.STATUS status;

    @Getter
    private UUID qrCode;

    @Getter @Setter
    @NotNull(message = "List of agreement services is mandatory!")
    @JsonProperty(value = "groupServiceIds")
    private Collection<GroupServiceDTOForContract> groupServices;

}
