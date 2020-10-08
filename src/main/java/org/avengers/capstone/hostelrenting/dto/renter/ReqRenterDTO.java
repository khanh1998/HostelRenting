package org.avengers.capstone.hostelrenting.dto.renter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.user.UserDTORegister;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author duattt on 9/29/20
 * @created 29/09/2020 - 09:46
 * @project youthhostelapp
 */
@Getter
@Setter
public class ReqRenterDTO extends UserDTORegister {
    @NotNull(message = "School id is mandatory")
    private Integer schoolId;

    @NotNull(message = "Province id is mandatory")
    private Integer provinceId;
}
