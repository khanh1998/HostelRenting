package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOCreate;
import org.avengers.capstone.hostelrenting.model.ContractImage;

import javax.validation.constraints.NotNull;

/**
 * @author duattt on 22/11/2020
 * @created 22/11/2020 - 18:17
 * @project youthhostelapp
 */
public class ContractImageDTOCreate extends ImageDTOCreate {
    @Getter
    @Setter
    @NotNull(message = "Type of image is mandatory")
    private ContractImage.TYPE type;

    @Override
    @JsonIgnore
    public void setDeleted(boolean isDeleted) {
        super.setDeleted(isDeleted);
    }
}
