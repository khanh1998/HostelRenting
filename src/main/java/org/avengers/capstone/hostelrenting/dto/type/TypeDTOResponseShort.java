package org.avengers.capstone.hostelrenting.dto.type;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 07:30
 * @project youthhostelapp
 */
@Setter @Getter
public class TypeDTOResponseShort {
    private int typeId;
    private float score;
    private int groupId;
    private String title;
    @JsonUnwrapped
    private CategoryDTO category;
    private float price;
    private String priceUnit;
    private TypeStatusDTO typeStatus;
    private Collection<TypeImageDTO> typeImages;

    public Collection<TypeImageDTO> getTypeImages() {
        return typeImages.stream().findFirst().stream().collect(Collectors.toList());
    }

    public String getPriceUnit() {
        return "triệu";
    }
}
