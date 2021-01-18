package org.avengers.capstone.hostelrenting.dto.combination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseV2;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TypeAndGroupInfoDTO implements Serializable {
    TypeDTOResponse type;
    GroupDTOResponseV2 group;
}