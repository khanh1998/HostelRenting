package org.avengers.capstone.hostelrenting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String hello() {
        return "Hostel Renting back-end API";
    }
}
