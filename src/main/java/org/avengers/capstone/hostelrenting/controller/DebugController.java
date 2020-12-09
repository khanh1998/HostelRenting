package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author duattt on 09/12/2020
 * @created 09/12/2020 - 15:35
 * @project youthhostelapp
 */
@RestController
@RequestMapping("/api/v1")
public class DebugController {
    @Value("${system.append.time.hours}")
    private Long appendingTime;

    @GetMapping("/currentTime")
    public ResponseEntity<?> getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.Format.DATE_TIME_DEFAULT);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newTime = now.plusHours(appendingTime);

        return ResponseEntity.ok(dtf.format(newTime));
    }
}
