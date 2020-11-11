package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Type;

import java.util.Collection;

/**
 * @author duattt on 11/11/20
 * @created 11/11/2020 - 09:48
 * @project youthhostelapp
 */
@Getter
@Setter
public class TypeDTO{
    private Integer typeId;
    private Float score;
    private String title;
    private Float price;
    private String priceUnit;
    private Float superficiality;
    private Integer capacity;
    private Integer view;
    private Integer depositTime;
    private String depositTimeUnit;
    @JsonIgnore
    private boolean isDeleted;

    //transient attributes
    private int schoolmate;
    private int compatriot;
    private int availableRoom;
    private int currentBooking;
    private int groupId;
    private Type.STATUS status;
    private Long createdAt;
    private Long updatedAt;
}
