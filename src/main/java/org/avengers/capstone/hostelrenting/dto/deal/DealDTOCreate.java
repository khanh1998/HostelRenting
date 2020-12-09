package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.UUID;


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
    private UUID renterId;
    @Getter
    @Setter
    private UUID vendorId;
    @Getter
    @Setter
    private float offeredPrice;
}
