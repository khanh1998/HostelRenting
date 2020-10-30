package org.avengers.capstone.hostelrenting.dto.combination;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
public class TypesAndGroupsDTO implements Serializable {
    private List<TypeDTOResponse> types;
    private int totalType;
    private Set<GroupDTOResponse> groups;
    private int totalGroup;
}
