package org.avengers.capstone.hostelrenting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class TypesAndGroupsDTO implements Serializable {
    Set<HostelTypeDTO> types;
    Set<HostelGroupDTO> groups;
}
