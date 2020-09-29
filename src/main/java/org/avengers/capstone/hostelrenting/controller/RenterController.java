package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOFull;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.MethodArgumentNotValidException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RenterController {
    private ModelMapper modelMapper;
    private RenterService renterService;
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
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }


    @PostMapping("/renters/register")
    public ResponseEntity<?> create(@Valid @RequestBody RenterDTOFull reqDTO) {
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        reqModel.setRole(roleService.findById(2));
        Renter createdModel = renterService.save(reqModel);

        RenterDTOFull resDTO = modelMapper.map(createdModel, RenterDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}")
    public ResponseEntity<?> update(@PathVariable Long renterId,
                                    @RequestBody RenterDTOFull reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(renterId);
        User resModel = renterService.update(reqDTO);
        RenterDTOFull resDTO = modelMapper.map(resModel, RenterDTOFull.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<?> getById(@PathVariable Long renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        RenterDTOFull resDTO = modelMapper.map(existedModel, RenterDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters")
    public ResponseEntity<?> getRenterByIds(@RequestParam Long[] renterIds) {
        List<RenterDTOFull> resDTOs = Arrays.stream(renterIds)
                .map(id -> modelMapper.map(renterService.findById(id), RenterDTOFull.class))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Renter information have been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

}
