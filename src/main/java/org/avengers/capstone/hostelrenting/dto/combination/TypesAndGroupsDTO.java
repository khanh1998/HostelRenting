package org.avengers.capstone.hostelrenting.dto.combination;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TypesAndGroupsDTO implements Serializable {
    Set<ResTypeDTO> types;
    Set<HostelGroupDTOFull> groups;
}
