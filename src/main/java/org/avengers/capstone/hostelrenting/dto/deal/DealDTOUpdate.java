package org.avengers.capstone.hostelrenting.dto.deal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponseShort;

/**
 * @author duattt on 30/11/2020
 * @created 30/11/2020 - 11:59
 * @project youthhostelapp
 */
public class DealDTOUpdate extends DealDTO{
    public DealDTOUpdate() {
        this.setUpdatedAt(System.currentTimeMillis());
    }

    @Override
    @JsonIgnore
    public void setCreatedAt(Long createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    @JsonIgnore
    public void setRenter(RenterDTOResponseShort renter) {
        super.setRenter(renter);
    }

    @Override
    @JsonIgnore
    public void setVendor(VendorDTOResponseShort vendor) {
        super.setVendor(vendor);
    }

    @Override
    @JsonIgnore
    public void setGroup(GroupDTOResponseShort group) {
        super.setGroup(group);
    }

    @Override
    @JsonIgnore
    public void setType(TypeDTOResponseShort type) {
        super.setType(null);
    }

    @Override
    @JsonIgnore
    public void setDealId(int dealId) {
        super.setDealId(dealId);
    }
}
