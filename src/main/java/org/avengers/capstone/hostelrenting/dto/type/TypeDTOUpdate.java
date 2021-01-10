package org.avengers.capstone.hostelrenting.dto.type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTO;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.model.TypeFacility;
import org.avengers.capstone.hostelrenting.model.TypeImage;

import java.util.Collection;

/**
 * @author duattt on 10/10/20
 * @created 10/10/2020 - 12:34
 * @project youthhostelapp
 */
@Getter
@Setter
@ToString
public class TypeDTOUpdate {
    private Integer typeId;
    private String title;
    private Integer categoryId;
    private Integer statusId;
    private Float price;
    private Float superficiality;
    private Integer capacity;
    private Float deposit;
    private String description;
    private Collection<Room> rooms;
    private Collection<TypeImage> imageUrls;
    private Collection<TypeFacility> facilities;
    private boolean isActive;
}
