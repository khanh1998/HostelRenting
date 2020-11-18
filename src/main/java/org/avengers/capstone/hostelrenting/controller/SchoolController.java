package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.SchoolDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SchoolController {
    private ModelMapper modelMapper;
    private SchoolService schoolService;

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Get list all of schools
     *
     * @return List of all schools
     */
    @GetMapping("/schools")
    public ResponseEntity<?> getAllProvinces() {
        String resMsg = "School(s) has been retrieved successfully!";
        List<SchoolDTOFull> resDTOs = schoolService.getAll()
                .stream()
                .map(school -> modelMapper.map(school, SchoolDTOFull.class))
                .collect(Collectors.toList());
        if (resDTOs.isEmpty())
            resMsg = "There is no school";

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

}
