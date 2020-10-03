package org.avengers.capstone.hostelrenting.dto.hosteltype;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqTypeDTO {
    private Integer typeId;
    private Integer groupId;
    private Integer categoryId;
    private Integer statusId;
    private String title;
    private float price;
    private float superficiality;
    private int capacity;
    private float deposit;
    private Integer[] facilityIds;
    private String[] imageUrls;
    private String[] roomNames;
}
