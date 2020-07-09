package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TypeStatusDTO {
    private int typeStatusId;
    private String typeStatusName;
    private List<HostelTypeDTO> hostelTypes;
}
