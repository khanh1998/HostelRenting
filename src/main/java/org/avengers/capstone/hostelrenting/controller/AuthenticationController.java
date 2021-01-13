package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.admin.AdminDTORequest;
import org.avengers.capstone.hostelrenting.dto.admin.AdminResponse;
import org.avengers.capstone.hostelrenting.dto.renter.RenterDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOResponse;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOLogin;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.model.Admin;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.service.impl.CustomUserService;
import org.avengers.capstone.hostelrenting.service.impl.FirebaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private CustomUserService customUserService;
    private ModelMapper modelMapper;
    private FirebaseService firebaseService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Autowired
    public void setCustomUserService(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTOLogin reqDTO) throws Exception {
        authenticate(reqDTO.getPhone(), reqDTO.getPassword());
        User resModel = customUserService.findByPhone(reqDTO.getPhone());
        UserDTOResponse resDTO;
            final UserDetails userDetails = customUserService.loadUserByUsername(reqDTO.getPhone());
            final String token = firebaseService.generateJwtToken(userDetails);
            if (resModel.getRole().equals(User.ROLE.RENTER)){
                resDTO = modelMapper.map(resModel, RenterDTOResponse.class);
            }
            else if (resModel.getRole().equals(User.ROLE.ADMIN)) {
                resDTO = modelMapper.map(resModel, UserDTOResponse.class);
            }
            else {
                resDTO = modelMapper.map(resModel, VendorDTOResponse.class);
            }
        resDTO.setJwtToken(token);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>( resDTO, "Login successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    private void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadCredentialsException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
