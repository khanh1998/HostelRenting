package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.StatisticDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
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
    public ResponseEntity<?> getStatistic(@RequestParam Integer[] streetIds) {
        List<StatisticDTO> resDTOs = statisticService.getStatisticByStreetWardIds(streetIds);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Get statistic successfully!");

        return ResponseEntity.ok(apiSuccess);
    }


}
