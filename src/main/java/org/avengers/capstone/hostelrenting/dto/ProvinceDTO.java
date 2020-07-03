package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProvinceDTO implements Serializable {
    private int provinceId;

    private String provinceName;
}
