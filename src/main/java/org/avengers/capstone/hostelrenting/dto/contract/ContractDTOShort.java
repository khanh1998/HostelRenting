package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContractDTOShort {
    private Integer contractId;
    @NotNull(message = "Room Id is mandatory")
    private Integer roomId;

    @NotNull(message = "Vendor Id is mandatory")
    private Integer vendorId;

    @NotNull(message = "Vendor Id is mandatory")
    private Integer renterId;

    private Integer dealId;

    private Integer bookingId;

    @NotNull(message = "Start time is mandatory")
    private Long startTime;

    @NotNull(message = "Duration is mandatory")
    private Integer duration;
}
