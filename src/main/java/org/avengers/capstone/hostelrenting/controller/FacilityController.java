package org.avengers.capstone.hostelrenting.controller;


import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FacilityController {
    private FacilityService facilityService;


    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * Get all facilities
     * @return ResponseEntity
     */
    @GetMapping("/facilities")
    public ResponseEntity<?> getAll(){
        String resMsg = "Facilities has been retrieved successfully!";
        List<FacilityDTO> resDTO = facilityService.getAll();
        if (resDTO.isEmpty()){
            resMsg = "There is no facility";
        }
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
