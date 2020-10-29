package org.avengers.capstone.hostelrenting.dto.province;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ProvinceDTOShort implements Serializable {
    private Integer provinceId;

    @NotNull(message = "Province name is mandatory!")
    private String provinceName;
}
