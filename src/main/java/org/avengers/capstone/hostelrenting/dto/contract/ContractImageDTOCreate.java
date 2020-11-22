package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOCreate;

/**
 * @author duattt on 22/11/2020
 * @created 22/11/2020 - 18:17
 * @project youthhostelapp
 */
public class ContractImageDTOCreate extends ImageDTOCreate {
    @Getter
    @Setter
    private boolean isReserved;
}
