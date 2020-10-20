package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.renter.ReqRenterDTO;
import org.avengers.capstone.hostelrenting.dto.renter.ResRenterDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.RoleService;
import org.avengers.capstone.hostelrenting.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RenterController {
    private ModelMapper modelMapper;
    private RenterService renterService;
    private RoleService roleService;
    private SchoolService schoolService;
    private ProvinceService provinceService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
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
    public ResponseEntity<?> create(@Valid @RequestBody ReqRenterDTO reqDTO) {
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        // set critical data for model: role, school, province
        reqModel.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
        reqModel.setRole(roleService.findById(2));
//        reqModel.setSchool(schoolService.findById(reqDTO.getSchoolId()));
//        reqModel.setProvince(provinceService.findById(reqDTO.getProvinceId()));
        Renter createdModel = renterService.save(reqModel);

        ResRenterDTO resDTO = modelMapper.map(createdModel, ResRenterDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}")
    public ResponseEntity<?> update(@PathVariable Long renterId,
                                    @RequestBody ResRenterDTO reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been up to date!";

        reqDTO.setUserId(renterId);
        User resModel = renterService.update(reqDTO);
        ResRenterDTO resDTO = modelMapper.map(resModel, ResRenterDTO.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<?> getById(@PathVariable Long renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        ResRenterDTO resDTO = modelMapper.map(existedModel, ResRenterDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters")
    public ResponseEntity<?> getRenterByIds(@RequestParam Long[] renterIds) {
        Set<ResRenterDTO> resDTOs = renterService.findByIds(Arrays.stream(renterIds).collect(Collectors.toSet())).stream()
                .map(renter -> modelMapper.map(renter, ResRenterDTO.class)).collect(Collectors.toSet());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Renter information have been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

}
