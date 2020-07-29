package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.TypeServiceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.model.TypeService;
import org.avengers.capstone.hostelrenting.service.HostelTypeServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class TypeServiceController {
    private HostelTypeServiceService typeServiceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setTypeServiceService(HostelTypeServiceService typeServiceService) {
        this.typeServiceService = typeServiceService;
    }

    @GetMapping("/types/{typeId}/services")
    public ResponseEntity<ApiSuccess> getServicesByTypeId(@PathVariable Integer typeId){
        List<TypeService> typeServices = typeServiceService.findByHostelTypeId(typeId);
        List<TypeServiceDTO> typeServiceDTOS = typeServices
                .stream()
                .map(ts-> modelMapper.map(ts, TypeServiceDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(typeServiceDTOS,"Retrieve successfully"));
    }
}
