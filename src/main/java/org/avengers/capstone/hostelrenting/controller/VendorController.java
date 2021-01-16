package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterBlockDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorBlockDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOCreate;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

    private ModelMapper modelMapper;
    private VendorService vendorService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
//        rqModel.setRole(roleService.findById(1));
        Vendor createdModel = vendorService.create(rqModel);

        VendorDTOResponse resDTO = modelMapper.map(createdModel, VendorDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<?> update(@PathVariable UUID vendorId,
                                    @RequestBody VendorDTOUpdate reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";
        Vendor exModel = vendorService.findById(vendorId);


        Vendor resModel = vendorService.updateInfo(exModel, reqDTO);
        VendorDTOResponse resDTO = modelMapper.map(resModel, VendorDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}/block")
    public ResponseEntity<?> blockInfo(@PathVariable UUID vendorId,
                                       @RequestBody @Valid VendorBlockDTO vendorBlockDTO) throws EntityNotFoundException {
        Vendor exModel = vendorService.findById(vendorId);
        exModel.setBlocked(vendorBlockDTO.isBlocked());
        Vendor resModel = vendorService.update(exModel);
        VendorDTOResponse resDTO = modelMapper.map(resModel, VendorDTOResponse.class);
        String resMsg = vendorBlockDTO.isBlocked() ? "This account has been blocked successfully!":"This account has been unblocked successfully!";
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/vendors/{vendorId}/token")
    public ResponseEntity<?> updateTokenOnly(@PathVariable UUID vendorId,
                                             @RequestBody @Valid UserDTOUpdateOnlyToken reqDTO) throws EntityNotFoundException {
        String resMsg = "Your token has been updated successfully!";
        Vendor exModel = vendorService.findById(vendorId);
        Vendor resModel = vendorService.updateToken(exModel, reqDTO);
        VendorDTOResponse resDTO = modelMapper.map(resModel, VendorDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<?> getById(@PathVariable UUID vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        VendorDTOResponse resDTO = modelMapper.map(existedModel, VendorDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/vendors")
    public ResponseEntity<?> getAllVendors(@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                           @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                           @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {
        String resMsg = "Your vendor(s) has been retrieved successfully!";

        List<VendorDTOResponse> resDTOs = vendorService.getAllVendors(page, size, sortBy, asc)
                .stream()
                .map(resDTO -> modelMapper.map(resDTO, VendorDTOResponse.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no vendor";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);
        apiSuccess.setPage(page);
        apiSuccess.setSize(size);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

}
