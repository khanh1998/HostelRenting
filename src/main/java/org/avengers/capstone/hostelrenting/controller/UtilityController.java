package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.utility.UCategoryDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.utility.UCategoryDTOShort;
import org.avengers.capstone.hostelrenting.model.UCategory;
import org.avengers.capstone.hostelrenting.service.UCategoryService;
import org.avengers.capstone.hostelrenting.service.UtilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UtilityController {
    private UtilityService utilityService;
    private UCategoryService uCategoryService;
    private ModelMapper modelMapper;

    @Autowired
    public void setuCategoryService(UCategoryService uCategoryService) {
        this.uCategoryService = uCategoryService;
    }

    @Autowired
    public void setUtilityService(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/utilities")
    public ResponseEntity<?> getNearbyUtilities(@RequestParam Double latitude,
                                             @RequestParam Double longitude,
                                             @RequestParam(defaultValue = "5.0") Double distance){
        List<UCategoryDTOFull> resDTOs = utilityService.getNearbyUtilities(latitude, longitude, distance)
                .stream()
                .map(model -> modelMapper.map(model, UCategoryDTOFull.class))
                .sorted(Comparator.comparingInt(UCategoryDTOFull::getDisplayOrder))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Utilities has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    @GetMapping("/ucategories")
    public ResponseEntity<?> getAllUCategory(){
        Collection<UCategory> resModels = uCategoryService.getAllUCategories();
        Collection<UCategoryDTOShort> resDTOs = resModels.stream().map( uCategory -> modelMapper.map(uCategory, UCategoryDTOShort.class))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Utilities has been retrieved successfully!");

        return ResponseEntity.ok(apiSuccess);
    }
}
