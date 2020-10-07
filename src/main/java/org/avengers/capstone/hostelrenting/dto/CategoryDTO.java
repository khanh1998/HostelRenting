package org.avengers.capstone.hostelrenting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private int displayOrder;
}
