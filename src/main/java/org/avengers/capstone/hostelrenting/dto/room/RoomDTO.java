package org.avengers.capstone.hostelrenting.dto.room;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoomDTO implements Serializable {
    private int roomId;
    private String roomName;
    private boolean isAvailable;
}
