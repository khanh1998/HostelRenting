package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;

@Data
public class ContractDTOShort {
    private Integer contractId;
    private Integer roomId;
    private Long vendorId;
    private Long renterId;
    private Integer dealId;
    private Integer bookingId;
    private Long startTime;
    private float duration;
    private String durationUnit;
}
