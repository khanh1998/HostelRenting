package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOCreate;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

    private ModelMapper modelMapper;
    private VendorService vendorService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/vendors/register")
    public ResponseEntity<?> create(@Valid @RequestBody VendorDTOCreate reqDTO) throws EntityNotFoundException {
        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
        rqModel.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
        rqModel.setRole(roleService.findById(1));
        Vendor createdModel = vendorService.create(rqModel);

        VendorDTOResponse resDTO = modelMapper.map(createdModel, VendorDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<?> update(@PathVariable Long vendorId,
                                    @RequestBody VendorDTOUpdate reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";
        Vendor exModel = vendorService.findById(vendorId);


        Vendor resModel = vendorService.updateInfo(exModel, reqDTO);
        VendorDTOResponse resDTO = modelMapper.map(resModel, VendorDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<?> getById(@PathVariable Long vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        VendorDTOResponse resDTO = modelMapper.map(existedModel, VendorDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
