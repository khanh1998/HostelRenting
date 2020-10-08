package org.avengers.capstone.hostelrenting.dto.deal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Deal;

@Getter
@Setter
public class DealDTOShort {
    private int dealId;
    private Deal.STATUS status;
    private int typeId;
    private Long renterId;
    private Long vendorId;
    private float offeredPrice;
}
