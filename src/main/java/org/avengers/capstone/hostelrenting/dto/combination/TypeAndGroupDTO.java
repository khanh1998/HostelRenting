package org.avengers.capstone.hostelrenting.dto.combination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class TypeAndGroupDTO implements Serializable {
    ResTypeDTO type;
    HostelGroupDTOFull groupDTOFull;
}
