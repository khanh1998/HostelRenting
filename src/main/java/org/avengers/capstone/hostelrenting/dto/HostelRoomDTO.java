package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HostelRoomDTO implements Serializable {
    private int roomId;
    private String roomName;
    private Integer typeId;
    private boolean isAvailable;
}
