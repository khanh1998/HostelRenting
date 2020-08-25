package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.UCategoryDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.service.UCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UtilityController {
    private UCategoryService uCategoryService;

    @Autowired
    public void setuCategoryService(UCategoryService uCategoryService) {
        this.uCategoryService = uCategoryService;
    }

    @GetMapping("/utilities")
    public ResponseEntity<?> getAllUtilities(){
        List<UCategoryDTO> resDTOs = uCategoryService.getAll();
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Utilities has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
