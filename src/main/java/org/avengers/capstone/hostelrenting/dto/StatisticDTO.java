package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StatisticDTO implements Serializable {
    private int id;
    private float avgPrice;
    private float avgSuperficiality;
    private long count;
    private int streetId;
}
