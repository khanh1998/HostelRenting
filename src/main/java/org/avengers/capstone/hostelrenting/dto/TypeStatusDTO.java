package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class TypeStatusDTO implements Serializable {
    private int statusId;
    private String statusName;
//    private List<HostelTypeDTO> hostelTypes;
}
