package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
import org.avengers.capstone.hostelrenting.model.Deal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealDTOFull {
    private int dealId;
    private Deal.STATUS status;
    private TypeDTOResponse type;
    private GroupDTOResponse group;
    private ResRenterDTO renter;
    private ResVendorDTO vendor;
    private float offeredPrice;

    private long createdAt;
    private long updatedAt;
}
