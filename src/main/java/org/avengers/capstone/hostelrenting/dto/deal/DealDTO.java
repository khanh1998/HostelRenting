package org.avengers.capstone.hostelrenting.dto.deal;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponseShort;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Type;

/**
 * @author duattt on 30/11/2020
 * @created 30/11/2020 - 11:46
 * @project youthhostelapp
 */
@Getter
@Setter
public class DealDTO {
    private int dealId;
    private Deal.STATUS status;
    private TypeDTOResponseShort type;
    private GroupDTOResponseShort group;
    private VendorDTOResponseShort vendor;
    private RenterDTOResponseShort renter;
    private float offeredPrice;

    private Long createdAt;
    private Long updatedAt;
}
