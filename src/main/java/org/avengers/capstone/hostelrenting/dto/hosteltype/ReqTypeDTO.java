package org.avengers.capstone.hostelrenting.dto.hosteltype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;

import java.util.Collection;

@Data
public class ReqTypeDTO {
    private int typeId;
    private float score;
    private int groupId;
    private Integer categoryId;
    private Integer statusId;
    private String title;
    private float price;
    private String priceUnit;
    private float superficiality;
    private int capacity;
    private int view;
    private float deposit;
    private boolean isDeleted;

    /**
     * Facility Id collection
     */
//    private Collection<Integer> facIds;

    /**
     * Image id collection
     */
//    private Collection<TypeImageDTO> ImgIds;

    /**
     * Service id collection
     */
//    private Collection<TypeServiceDTO> services;
}
