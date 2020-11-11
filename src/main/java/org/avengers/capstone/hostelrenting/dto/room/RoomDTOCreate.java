package org.avengers.capstone.hostelrenting.dto.room;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/11/20
 * @created 11/11/2020 - 09:06
 * @project youthhostelapp
 */
public class RoomDTOCreate extends RoomDTO{
    public RoomDTOCreate() {
        this.setAvailable(true);
    }

    @Override
    @JsonIgnore
    public void setRoomId(int roomId) {
        super.setRoomId(roomId);
    }
}
