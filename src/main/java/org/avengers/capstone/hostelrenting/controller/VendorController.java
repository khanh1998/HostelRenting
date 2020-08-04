package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

    private ModelMapper modelMapper;
    private VendorService vendorService;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

//    @PostMapping("/vendors/register")
//    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody VendorDTOFull reqDTO) throws EntityNotFoundException {
//        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
//        Vendor createdModel = vendorService.save(rqModel);
//
//        VendorDTOFull resDTO = modelMapper.map(createdModel, VendorDTOFull.class);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Vendor.class.getSimpleName())));
//    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<?> update(@PathVariable Integer vendorId,
                                    @RequestBody UserDTOFull reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(vendorId);
        User resModel = vendorService.update(reqDTO);
        UserDTOFull resDTO = modelMapper.map(resModel, UserDTOFull.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<?> getById(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        UserDTOFull resDTO = modelMapper.map(existedModel, UserDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
