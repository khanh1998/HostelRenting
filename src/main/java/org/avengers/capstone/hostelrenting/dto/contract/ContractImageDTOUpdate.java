package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOUpdate;
import org.avengers.capstone.hostelrenting.model.ContractImage;

import javax.validation.constraints.NotNull;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 10:02
 * @project youthhostelapp
 */
public class ContractImageDTOUpdate extends ImageDTOUpdate {
    @Getter
    @Setter
    @NotNull(message = "Type of image is mandatory")
    private ContractImage.TYPE type;
}
