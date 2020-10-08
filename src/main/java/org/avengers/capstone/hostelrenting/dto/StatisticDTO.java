package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StatisticDTO implements Serializable {
    private int id;
    private float avgPrice;
    private float avgSuperficiality;
    private long count;
    private int streetId;
}
