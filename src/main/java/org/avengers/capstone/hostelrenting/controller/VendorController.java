package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTORegister;
import org.avengers.capstone.hostelrenting.dto.vendor.ResVendorDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.User;
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
    public ResponseEntity<?> create(@Valid @RequestBody UserDTORegister reqDTO) throws EntityNotFoundException {
        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
        rqModel.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
        rqModel.setRole(roleService.findById(1));
        Vendor createdModel = vendorService.save(rqModel);

        ResVendorDTO resDTO = modelMapper.map(createdModel, ResVendorDTO.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<?> update(@PathVariable Long vendorId,
                                    @RequestBody ResVendorDTO reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(vendorId);
        User resModel = vendorService.update(reqDTO);
        ResVendorDTO resDTO = modelMapper.map(resModel, ResVendorDTO.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<?> getById(@PathVariable Long vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        ResVendorDTO resDTO = modelMapper.map(existedModel, ResVendorDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
