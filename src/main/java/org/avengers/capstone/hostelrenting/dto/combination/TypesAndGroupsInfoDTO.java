package org.avengers.capstone.hostelrenting.dto.combination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseV2;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseV3;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
public class TypesAndGroupsInfoDTO implements Serializable {
    private List<TypeDTOResponse> types;
    private int totalType;
    private Set<GroupDTOResponseV3> groups;
    private int totalGroup;
    private List<VendorDTOResponse> vendors;
}
