package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.util.List;

@Data
public class HostelRoomDTO {
    private int roomId;
    private String roomName;
    private Integer typeId;
    private boolean isAvailable;
    private List<ContractDTO> contracts;
}
