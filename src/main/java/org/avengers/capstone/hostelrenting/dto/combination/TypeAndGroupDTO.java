package org.avengers.capstone.hostelrenting.dto.combination;

import lombok.*;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TypeAndGroupDTO implements Serializable {
    ResTypeDTO type;
    HostelGroupDTOFull groupDTOFull;
}
