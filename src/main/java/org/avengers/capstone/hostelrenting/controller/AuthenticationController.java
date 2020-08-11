package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.user.UserDTOFull;
import org.avengers.capstone.hostelrenting.model.User;
import org.avengers.capstone.hostelrenting.service.UserService;
import org.avengers.capstone.hostelrenting.service.impl.CustomUserService;
import org.avengers.capstone.hostelrenting.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private CustomUserService customUserService;
    private ModelMapper modelMap;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setCustomUserService(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @Autowired
    public void setUserService(ModelMapper modelMap) {
        this.modelMap = modelMap;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User reqDTO) throws Exception {

        authenticate(reqDTO.getPhone(), reqDTO.getPassword());
        final UserDetails userDetails = customUserService.loadUserByUsername(reqDTO.getPhone());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User resModel = customUserService.findByPhone(reqDTO.getPhone());
        UserDTOFull resDTO = modelMap.map(resModel, UserDTOFull.class);
        resDTO.setJwtToken(token);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Login successfully!");

        return ResponseEntity.ok(apiSuccess);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadCredentialsException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
