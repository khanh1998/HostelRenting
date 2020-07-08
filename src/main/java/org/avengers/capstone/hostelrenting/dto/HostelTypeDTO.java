package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostelTypeDTO implements Serializable {
    private int hostelTypeId;
    private int hostelGroupId;
//    private int categoryId;
//    private int typeStatusId;
    private String hostelTypeName;
    private long price;
    private float superficiality;
    private int capacity;
    private boolean isDeleted;
}
