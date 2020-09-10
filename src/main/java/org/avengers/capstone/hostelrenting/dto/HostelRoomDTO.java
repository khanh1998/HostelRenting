package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOFull;

import java.io.Serializable;
import java.util.List;

@Data
public class HostelRoomDTO implements Serializable {
    private int roomId;
    private String roomName;
    private Integer typeId;
    private boolean isAvailable;
}
