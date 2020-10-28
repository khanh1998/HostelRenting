package org.avengers.capstone.hostelrenting.model.serialized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressFull {
    private Integer provinceId;
    private String provinceName;
    private Integer districtId;
    private String districtName;
    private Integer wardId;
    private String wardName;
    private Integer streetId;
    private String streetName;
    private Integer streetWardId;
}
