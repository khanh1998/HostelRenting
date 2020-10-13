package org.avengers.capstone.hostelrenting.dto.statistic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author duattt on 10/13/20
 * @created 13/10/2020 - 12:59
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder
public class StatisticStreet {
    private long count;
    private float avgPrice;
    private float avgSuperficality;
    private int streetId;
    private String streetName;
}
