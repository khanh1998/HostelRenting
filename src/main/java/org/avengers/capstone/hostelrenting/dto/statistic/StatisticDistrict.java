package org.avengers.capstone.hostelrenting.dto.statistic;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author duattt on 10/13/20
 * @created 13/10/2020 - 12:57
 * @project youthhostelapp
 */
@Getter
@Setter
@Builder
@JsonPropertyOrder
public class StatisticDistrict {
    private int districtId;
    private String districtName;
    private float avgPrice;
    private float avgSuperficiality;
    private long count;
    private Collection<StatisticWard> wards;
}
