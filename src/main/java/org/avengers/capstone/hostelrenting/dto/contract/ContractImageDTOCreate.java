package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Override
    @JsonIgnore
    public void setDeleted(boolean isDeleted) {
        super.setDeleted(isDeleted);
    }
}
