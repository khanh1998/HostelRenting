package org.avengers.capstone.hostelrenting.dto.type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author duattt on 10/10/20
 * @created 10/10/2020 - 12:34
 * @project youthhostelapp
 */
@Getter
@Setter
public class TypeDTOUpdate {
    private Integer typeId;
    private String title;
    private Integer categoryId;
    private Integer statusId;
    private Float price;
    private Float superficiality;
    private Integer capacity;
    private Float deposit;
}
