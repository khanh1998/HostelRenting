package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.model.HostelType;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;
}
