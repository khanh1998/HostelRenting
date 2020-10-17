package org.avengers.capstone.hostelrenting.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.model.Contract;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author duattt on 10/17/20
 * @created 17/10/2020 - 11:47
 * @project youthhostelapp
 */

public class ContractDTOUpdate implements Serializable {

    public ContractDTOUpdate() {
        this.updatedAt = System.currentTimeMillis();
        this.status = Contract.STATUS.ACTIVATED;
    }

    @Getter
    @Setter
    @NotNull(message = "QrCode id is mandatory!")
    private UUID qrCode;

    @Getter
    private final Contract.STATUS status;
    @Getter
    private final Long updatedAt;
}
