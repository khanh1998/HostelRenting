package org.avengers.capstone.hostelrenting.dto.combination;

import lombok.*;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TypeAndGroupDTO implements Serializable {
    TypeDTOResponse type;
    GroupDTOResponse groupDTOFull;
}
