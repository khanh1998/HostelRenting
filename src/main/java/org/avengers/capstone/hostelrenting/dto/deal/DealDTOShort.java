package org.avengers.capstone.hostelrenting.dto.deal;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.Deal;

@Data
public class DealDTOShort {
    private int dealId;
    private Deal.STATUS status;
    private int typeId;
    private int renterId;
    private int vendorId;
    private float offeredPrice;
}
