package org.avengers.capstone.hostelrenting.dto.statistic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author duattt on 10/13/20
 * @created 13/10/2020 - 12:14
 * @project youthhostelapp
 */
@Setter @Getter @Builder
public class StatisticDTOResponse {
    private long count;
    private float avgPrice;
    private float avgSuperficality;
    private Collection<StatisticProvince> provinces;
}
