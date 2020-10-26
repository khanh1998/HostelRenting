package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.statistic.Statistic;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.statistic.StatisticDTOResponse;
import org.avengers.capstone.hostelrenting.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StatisticController {


    private StatisticService statisticService;


    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatisticByStreetIds(@RequestParam Integer[] ids) {
        StatisticDTOResponse resDTO = statisticService.getStatisticByDistrictId(ids);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Get statistic successfully!");

        return ResponseEntity.ok(apiSuccess);
    }


}
