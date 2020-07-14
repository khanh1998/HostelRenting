package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

@Data
public class DealDTO {
    private int dealId;
    private int typeId;
    private int renterId;
    private int vendorId;
    private long offeredPrice;
//    private boolean isDeleted;
}
