package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.TypeImage;

import java.io.Serializable;
import java.util.Collection;

@Data
public class HostelTypeDTO implements Serializable {
    private int typeId;
    private int groupId;
    private int categoryId;
    private int typeStatusId;
    private String typeName;
    private long price;
    private float superficiality;
    private int capacity;
    private boolean isDeleted;

    Collection<HostelRoomDTO> hostelRooms;
    Collection<FacilityDTO> facilities;
    Collection<TypeImage> typeImages;
}
