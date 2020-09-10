package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.model.HostelType;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private int displayOrder;
}
