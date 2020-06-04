package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/renter")
public class RenterController {
    @Autowired
    private RenterService renterService;
    @PostMapping
    public Renter createRenter(@Param("username") String username, @RequestParam("name") String name) {
        System.out.println(username + name);
        return renterService.createRenter(username, name);
    }

    @GetMapping
    public List<Renter> getAllRenters() {
        return renterService.getAll();
    }
}
