package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOFull;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HostelRoomDTO implements Serializable {
    private int roomId;
    private String roomName;
    private Integer typeId;
    private boolean isAvailable;
}
