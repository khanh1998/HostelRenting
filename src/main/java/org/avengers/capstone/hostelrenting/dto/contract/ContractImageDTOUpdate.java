package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOUpdate;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 10:02
 * @project youthhostelapp
 */
public class ContractImageDTOUpdate extends ImageDTOUpdate {
    @Getter
    @Setter
    private boolean isReserved;
}
