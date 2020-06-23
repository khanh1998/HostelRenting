package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewDetailDTO implements Serializable {
    private int hostelGroupId;
    private String hostelTypeName;
    private String price;
    private float superficiality;
    private int capacity;
    private String hostelGroupName;
    private String detailedAddress;
    private String vendorName;
    private String avatar;
}
