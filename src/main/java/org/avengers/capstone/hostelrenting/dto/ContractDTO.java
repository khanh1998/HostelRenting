package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ContractDTO {
    private int contractId;
    private int roomId;
    private int vendorId;
    private int renterId;
    private int dealId;
    private int bookingId;
    private String startTime;
    private int duration;
//    private boolean isDeleted;
}
