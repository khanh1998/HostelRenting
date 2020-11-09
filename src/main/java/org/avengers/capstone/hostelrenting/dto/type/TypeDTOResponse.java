package org.avengers.capstone.hostelrenting.dto.type;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeDTOResponse implements Serializable {
    private int typeId;
    private float score;
    private int groupId;
    private String title;
    private TypeStatusDTO typeStatus;
    private float price;
    private String priceUnit;
    private float superficiality;
    private int capacity;
    private int view;
    private float deposit;

    /* Transient fields */
    // the same school
    private int schoolMate;
    // the same hometown
    private int compatriot;
    private int availableRoom;
    private int currentBooking;
    /* End of transient fields */

    private boolean isDeleted;

    private Collection<FacilityDTO> facilities;
    private Collection<TypeImageDTO> typeImages;

}
