package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.statistic.Statistic;
import org.avengers.capstone.hostelrenting.dto.statistic.StatisticDTOResponse;

import java.util.List;

public interface StatisticService {
    StatisticDTOResponse getStatisticByDistrictId(Integer[] ids);
}
