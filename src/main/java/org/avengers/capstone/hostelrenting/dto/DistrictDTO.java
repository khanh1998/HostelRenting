package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.Province;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class DistrictDTO implements Serializable {
    private int districtId;

    private String districtName;

    private int provinceId;
//
//    private int hostelGroupId;
}
