package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterBlockDTO;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOCreate;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOUpdateOnlyToken;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class RenterController {
    private ModelMapper modelMapper;
    private RenterService renterService;
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
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }


    @PostMapping("/renters/register")
    public ResponseEntity<?> registerRenter(@Valid @RequestBody RenterDTOCreate reqDTO) {
        Renter reqModel = modelMapper.map(reqDTO, Renter.class);
        // set critical data for model: role, school, province
        reqModel.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
//        reqModel.setRole(roleService.findById(2));
        Renter createdModel = renterService.create(reqModel);

        RenterDTOResponse resDTO = modelMapper.map(createdModel, RenterDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your account has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}")
    public ResponseEntity<?> updateInfo(@PathVariable UUID renterId,
                                    @RequestBody @Valid RenterDTOUpdate reqDTO) throws EntityNotFoundException {
        String resMsg = "Your information has been updated successfully!";
        Renter exModel = renterService.findById(renterId);
        Renter resModel = renterService.updateInfo(exModel, reqDTO);
        RenterDTOResponse resDTO = modelMapper.map(resModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}/block")
    public ResponseEntity<?> blockInfo(@PathVariable UUID renterId,
                                       @RequestBody @Valid RenterBlockDTO renterBlockDTO) throws EntityNotFoundException {
        Renter exModel = renterService.findById(renterId);
        exModel.setBlocked(renterBlockDTO.isBlocked());
        Renter resModel = renterService.update(exModel);
        RenterDTOResponse resDTO = modelMapper.map(resModel, RenterDTOResponse.class);
        String resMsg = renterBlockDTO.isBlocked() ? "This account has been blocked successfully!":"This account has been unblocked successfully!";
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/renters/{renterId}/token")
    public ResponseEntity<?> updateTokenOnly(@PathVariable UUID renterId,
                                    @RequestBody @Valid UserDTOUpdateOnlyToken reqDTO) throws EntityNotFoundException {
        String resMsg = "Your token has been updated successfully!";
        Renter exModel = renterService.findById(renterId);
        Renter resModel = renterService.updateToken(exModel, reqDTO);
        RenterDTOResponse resDTO = modelMapper.map(resModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}")
    public ResponseEntity<?> getById(@PathVariable UUID renterId) throws EntityNotFoundException {
        Renter existedModel = renterService.findById(renterId);
        RenterDTOResponse resDTO = modelMapper.map(existedModel, RenterDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your information has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters")
    public ResponseEntity<?> getRenterByIds(@RequestParam UUID[] renterIds) {
        Set<RenterDTOResponse> resDTOs = renterService.findByIds(Arrays.stream(renterIds).collect(Collectors.toSet())).stream()
                .map(renter -> modelMapper.map(renter, RenterDTOResponse.class)).collect(Collectors.toSet());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Renter information have been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/renters")
    public ResponseEntity<?> getAllRenters(@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                    @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {
        String resMsg = "Your renter(s) has been retrieved successfully!";

        List<RenterDTOResponse> resDTOs = renterService.getAllRenters(page, size, sortBy, asc)
                .stream()
                .map(resDTO -> modelMapper.map(resDTO, RenterDTOResponse.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no renter";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);
        apiSuccess.setPage(page);
        apiSuccess.setSize(size);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
