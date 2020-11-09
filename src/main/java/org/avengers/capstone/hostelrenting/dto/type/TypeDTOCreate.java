package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


public class TypeDTOCreate {
    public TypeDTOCreate() {
        this.statusId = 1;
    }

    @Getter
    @Setter
    @JsonIgnore
    private Integer typeId;

    @Getter
    private Integer statusId;

    @Getter
    @Setter
    private String title;

    @NotNull(message = "price is mandatory!")
    @Getter
    @Setter
    private Float price;

    @Setter
    @Getter
    @NotNull(message = "Price unit is mandatory")
    private String priceUnit;

    @NotNull(message = "superficiality is mandatory!")
    @Getter
    @Setter
    private Float superficiality;

    @NotNull(message = "capacity is mandatory!")
    @Getter
    @Setter
    private Integer capacity;

    @NotNull(message = "deposit is mandatory!")
    @Getter
    @Setter
    private Float deposit;

    @Getter
    @Setter
    private Integer[] facilityIds;
    @Getter
    @Setter
    private String[] imageUrls;

    @NotNull(message = "roomNames is mandatory!")
    @Getter
    @Setter
    private String[] roomNames;
}
