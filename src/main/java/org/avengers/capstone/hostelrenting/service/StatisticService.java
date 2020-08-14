package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.StatisticDTO;

import java.util.List;

public interface StatisticService {
    List<StatisticDTO> getStatisticByStreetIds(Integer[] ids);
}
