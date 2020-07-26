package org.avengers.capstone.hostelrenting.dto.deal;

import lombok.Data;

@Data
public class DealDTOShort {
    private int dealId;
    private int typeId;
    private int renterId;
    private int vendorId;
    private float offeredPrice;
//    private boolean isDeleted;
}