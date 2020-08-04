package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ServiceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * Get all services
     * @return ResponseEntity
     */
    @GetMapping("/services")
    public ResponseEntity<?> getAll(){
        String resMsg = "Services has been retrieved successfully!";
        List<ServiceDTO> resDTO = serviceService.getAll();
        if (resDTO.isEmpty()){
            resMsg = "There is no service";
        }
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
