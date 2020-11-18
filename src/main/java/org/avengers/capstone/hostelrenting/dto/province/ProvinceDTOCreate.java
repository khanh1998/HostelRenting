package org.avengers.capstone.hostelrenting.dto.province;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author duattt on 11/18/20
 * @created 18/11/2020 - 11:45
 * @project youthhostelapp
 */

public class ProvinceDTOCreate extends ProvinceDTO {
    @Override
    @JsonIgnore
    public Integer getProvinceId() {
        return super.getProvinceId();
    }
}
