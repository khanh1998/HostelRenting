package org.avengers.capstone.hostelrenting.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOFull;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOShort;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StreetWardDTO implements Serializable {
    private Integer streetWardId;
    private StreetDTO street;
    private WardDTOShort ward;

}
