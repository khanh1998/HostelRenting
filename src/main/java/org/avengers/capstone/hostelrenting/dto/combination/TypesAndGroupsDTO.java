package org.avengers.capstone.hostelrenting.dto.combination;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TypesAndGroupsDTO implements Serializable {
    Set<TypeDTOResponse> types;
    Set<GroupDTOResponse> groups;
}
