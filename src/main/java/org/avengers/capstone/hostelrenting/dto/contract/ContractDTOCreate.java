package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupServiceDTOCreate;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.GroupService;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
public class ContractDTOCreate {

    public ContractDTOCreate() {
        this.qrCode = UUID.randomUUID();
        this.status = Contract.STATUS.INACTIVE;
        this.createdAt = System.currentTimeMillis();
    }

    private Integer contractId;

    @NotNull(message = "Room id is mandatory!")
    private Integer roomId;

    @NotNull(message = "Vendor id is mandatory!")
    private Long vendorId;

    @NotNull(message = "Renter id is mandatory!")
    private Long renterId;

    private Integer dealId;

    private Integer bookingId;

    @NotNull(message = "Start time is mandatory!")
    private Long startTime;

    @NotNull(message = "Contract duration is mandatory!")
    private float duration;

    private String evidenceImgUrl;

    private Long createdAt;

    private Contract.STATUS status;

    private UUID qrCode;

    @NotNull(message = "List of agreement services is mandatory!")
    private Collection<GroupServiceDTOCreate> groupServices;
}
