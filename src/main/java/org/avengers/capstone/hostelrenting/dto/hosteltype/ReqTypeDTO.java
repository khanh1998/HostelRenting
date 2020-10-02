package org.avengers.capstone.hostelrenting.dto.hosteltype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;

import java.util.Collection;

@Data
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
//    private Collection<Integer> facilityIds;
}
