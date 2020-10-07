package org.avengers.capstone.hostelrenting.dto.type;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TypeDTOCreate {
    private Integer typeId;

    @NotNull(message = "groupId is mandatory!")
    private Integer groupId;

    @NotNull(message = "categoryId is mandatory!")
    private Integer categoryId;

    @NotNull(message = "statusId is mandatory!")
    private Integer statusId;

    private String title;

    @NotNull(message = "price is mandatory!")
    private Float price;

    @NotNull(message = "superficiality is mandatory!")
    private Float superficiality;

    @NotNull(message = "capacity is mandatory!")
    private Integer capacity;

    @NotNull(message = "deposit is mandatory!")
    private Float deposit;

    private Integer[] facilityIds;
    private String[] imageUrls;

    @NotNull(message = "roomNames is mandatory!")
    private String[] roomNames;
}
