package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOFull;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.MethodArgumentNotValidException;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

    private ModelMapper modelMapper;
    private VendorService vendorService;
    private RoleService roleService;

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
    public ResponseEntity<?> create(@Valid @RequestBody VendorDTOFull reqDTO) throws EntityNotFoundException {
        if (reqDTO.getRole().getRoleId() != 1){
            throw new MethodArgumentNotValidException(Vendor.class, "roleId", String.valueOf(reqDTO.getRole().getRoleId()));
        }
        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
        rqModel.setRole(roleService.findById(reqDTO.getRole().getRoleId()));
        Vendor createdModel = vendorService.save(rqModel);

        VendorDTOFull resDTO = modelMapper.map(createdModel, VendorDTOFull.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<?> update(@PathVariable Long vendorId,
                                    @RequestBody VendorDTOFull reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(vendorId);
        User resModel = vendorService.update(reqDTO);
        VendorDTOFull resDTO = modelMapper.map(resModel, VendorDTOFull.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<?> getById(@PathVariable Long vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        VendorDTOFull resDTO = modelMapper.map(existedModel, VendorDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
