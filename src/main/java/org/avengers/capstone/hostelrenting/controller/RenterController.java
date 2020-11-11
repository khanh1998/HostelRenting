package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOCreate;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RenterController {
    private ModelMapper modelMapper;
    private RenterService renterService;
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
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }


    @PostMapping("/renters/register")
    public ResponseEntity<?> registerRenter(@Valid @RequestBody RenterDTOCreate reqDTO) {
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        // set critical data for model: role, school, province
        reqModel.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
        reqModel.setRole(roleService.findById(2));
        Renter createdModel = renterService.create(reqModel);

        RenterDTOResponse resDTO = modelMapper.map(createdModel, RenterDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}")
    public ResponseEntity<?> updateInfo(@PathVariable Long renterId,
                                    @RequestBody @Valid RenterDTOUpdate reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been updated successfully!";
        Renter exModel = renterService.findById(renterId);
        Renter resModel = renterService.updateInfo(exModel, reqDTO);
        RenterDTOResponse resDTO = modelMapper.map(resModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}/token")
    public ResponseEntity<?> updateTokenOnly(@PathVariable Long renterId,
                                    @RequestBody @Valid UserDTOUpdateOnlyToken reqDTO) throws EntityNotFoundException {
        String resMsg = "Your token has been updated successfully!";
        Renter exModel = renterService.findById(renterId);
        Renter resModel = renterService.updateToken(exModel, reqDTO);
        RenterDTOResponse resDTO = modelMapper.map(resModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<?> getById(@PathVariable Long renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        RenterDTOResponse resDTO = modelMapper.map(existedModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters")
    public ResponseEntity<?> getRenterByIds(@RequestParam Long[] renterIds) {
        Set<RenterDTOResponse> resDTOs = renterService.findByIds(Arrays.stream(renterIds).collect(Collectors.toSet())).stream()
                .map(renter -> modelMapper.map(renter, RenterDTOResponse.class)).collect(Collectors.toSet());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Renter information have been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

}
