package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponseShort;

import java.util.UUID;

/**
 * @author duattt on 18/12/2020
 * @created 18/12/2020 - 17:06
 * @project youthhostelapp
 */
public class DealDTOResponseShort extends DealDTO{

    @Override
    @JsonIgnore
    public TypeDTOResponseShort getType() {
        return super.getType();
    }

    @Override
    @JsonIgnore
    public GroupDTOResponseShort getGroup() {
        return super.getGroup();
    }

    @Override
    @JsonIgnore
    public VendorDTOResponseShort getVendor() {
        return super.getVendor();
    }

    @Override
    @JsonIgnore
    public RenterDTOResponseShort getRenter() {
        return super.getRenter();
    }

    @Getter @Setter
    private UUID vendorId;

    @Getter @Setter
    private UUID renterId;

    @Getter @Setter
    private int typeId;
}
