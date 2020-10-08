package org.avengers.capstone.hostelrenting.dto.hosteltype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;

import java.io.Serializable;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResTypeDTO implements Serializable {
    private int typeId;
    private float score;
    private int groupId;
    private CategoryDTO category;
    private TypeStatusDTO typeStatus;
    private String title;
    private float price;
    private String priceUnit;
    private float superficiality;
    private int capacity;
    private int view;
    private float deposit;
    // the same school
    private int schoolMate;
    // the same hometown
    private int compatriot;
    private boolean isDeleted;

    private Collection<FacilityDTO> facilities;
    private Collection<TypeImageDTO> typeImages;
}