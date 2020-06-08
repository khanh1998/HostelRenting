package org.avengers.capstone.hostelrenting.controller;

import io.swagger.annotations.Api;
import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/renter")
@Api(value = "")
public class RenterController {
    private RenterService renterService;

    @PostMapping
    public Renter createRenter(@RequestBody RenterDTO dto) {
        return renterService.createRenter(dto);
    }

    @GetMapping
    public List<Renter> getAllRenters() {
        return renterService.getAll();
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }
}
