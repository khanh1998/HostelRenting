package org.avengers.capstone.hostelrenting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class TypesAndGroupsDTO implements Serializable {
    Set<ResTypeDTO> types;
    Set<HostelGroupDTO> groups;
}
