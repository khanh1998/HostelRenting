package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOFull;

import java.util.List;

@Data
public class HostelRoomDTO {
    private int roomId;
    private String roomName;
    private Integer typeId;
    private boolean isAvailable;
    private List<ContractDTOFull> contracts;
}
