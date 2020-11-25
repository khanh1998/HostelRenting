package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.street.StreetDTOCreate;
import org.avengers.capstone.hostelrenting.dto.street.StreetDTOResponse;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOCreate;
import org.avengers.capstone.hostelrenting.dto.ward.WardDTOResponse;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.Street;
import org.avengers.capstone.hostelrenting.model.Ward;
import org.avengers.capstone.hostelrenting.service.StreetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author duattt on 24/11/2020
 * @created 24/11/2020 - 10:21
 * @project youthhostelapp
 */
@Controller
@RequestMapping("/api/v1")
public class StreetController {
    private StreetService streetService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    @PostMapping("/streets")
    public ResponseEntity<?> createNewStreets(@Validated @RequestBody Collection<StreetDTOCreate> reqDTOs) {
        Collection<Street> reqModels = reqDTOs.stream()
                .map(streetDTOCreate -> modelMapper.map(streetDTOCreate, Street.class))
                .collect(Collectors.toList());

        Collection<StreetDTOResponse> resDTOs = reqModels.stream().map(reqModel -> {
            Street resModel = streetService.createIfNotExist(reqModel);
            return modelMapper.map(resModel, StreetDTOResponse.class);
        }).collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Streets has been created successfully!");
        apiSuccess.setTotal(resDTOs.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }
}
