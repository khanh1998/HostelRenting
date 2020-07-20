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
    private String title;
    private float price;
    private String priceUnit;
    private float superficiality;
    private int capacity;
    private int view;
    private boolean isDeleted;

    private Collection<HostelRoomDTO> hostelRooms;
    private Collection<FacilityDTO> facilities;
    private Collection<TypeImageDTO> typeImages;
    private Collection<ServiceDTO> services;
}
