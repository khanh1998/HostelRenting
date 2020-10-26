package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.RegulationDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.RegulationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duattt on 10/10/20
 * @created 10/10/2020 - 11:43
 * @project youthhostelapp
 */
@RestController
@RequestMapping("/api/v1")
public class RegulationController {
    private RegulationService regulationService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRegulationService(RegulationService regulationService) {
        this.regulationService = regulationService;
    }

    @GetMapping("/regulations")
    public ResponseEntity<?> getAllRegulation(){
        String resMsg = "Regulations has been retrieved successfully!";
        Collection<RegulationDTO> resDTOs = regulationService.getAllRegulation()
                .stream()
                .map(model -> modelMapper.map(model, RegulationDTO.class))
                .collect(Collectors.toList());
        if (resDTOs.isEmpty()){
            resMsg = "There is no regulation";
        }

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
