package org.avengers.capstone.hostelrenting.dto.contract;

import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.image.ImageDTOResponse;

/**
 * @author duattt on 23/11/2020
 * @created 23/11/2020 - 08:56
 * @project youthhostelapp
 */
public class ContractImageDTOResponse extends ImageDTOResponse {
    @Setter
    @Getter
    private boolean isReserved;
}