package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.service.TypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author duattt on 13/12/2020
 * @created 13/12/2020 - 11:23
 * @project youthhostelapp
 */
@RestController
@RequestMapping("/api/v1/admin/management")
public class AdminController {
    private TypeService typeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @PutMapping("/types/{typeId}")
    public ResponseEntity<?> censorType(@PathVariable Integer typeId, boolean isActive) {
        Type reqModel = typeService.findById(typeId);
        if (reqModel.isActive()!=isActive){
            reqModel.setActive(isActive);
            Type resModel = typeService.update(reqModel);
            TypeDTOResponse resDTO =modelMapper.map(resModel, TypeDTOResponse.class);
            // Response entity
            ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your booking has been retrieved successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
        }
        return ResponseEntity.badRequest().body(null);

    }
}
