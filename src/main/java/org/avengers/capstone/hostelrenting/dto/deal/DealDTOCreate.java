package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Deal;


public class DealDTOCreate {
    @Getter
    @Setter
    @JsonIgnore
    private int dealId;
    @Getter
    @Setter
    private Deal.STATUS status;
    @Getter
    @Setter
    private int typeId;
    @Getter
    @Setter
    private Long renterId;
    @Getter
    @Setter
    private Long vendorId;
    @Getter
    @Setter
    private float offeredPrice;
}
