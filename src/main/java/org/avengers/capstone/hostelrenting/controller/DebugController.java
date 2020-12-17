package org.avengers.capstone.hostelrenting.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.handler.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @Value("${system.append.time.hours}")
    private Long appendingTime;

    @GetMapping("/systems/appendtime")
    public ResponseEntity<?> getAppendTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.Format.DATE_TIME_DEFAULT);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newTime = now.plusHours(appendingTime);

        return ResponseEntity.ok("CURRENT_TIME:" + dtf.format(newTime));
    }

    @PostMapping("/systems/appendtime")
    public ResponseEntity<?> changeAppendTime(Integer appendTime) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("application.properties");
            config.setProperty("system.append.time.hours", appendTime);
            config.save();
        } catch (ConfigurationException e) {
           logger.error(e.getLocalizedMessage());
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.Format.DATE_TIME_DEFAULT);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newTime = now.plusHours(appendTime);

        return ResponseEntity.ok("CURRENT_TIME: " + dtf.format(newTime));
    }
}
