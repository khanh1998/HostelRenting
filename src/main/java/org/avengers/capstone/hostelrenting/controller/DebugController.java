package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.Constant;
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

    @GetMapping("/currentTime")
    public ResponseEntity<?> getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.Format.DATE_TIME_DEFAULT);
        LocalDateTime now = LocalDateTime.now();

        return ResponseEntity.ok(dtf.format(now));
    }
}
