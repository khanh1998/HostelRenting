package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    @PostMapping("/login")
//    public ResponseEntity<ApiSuccess> loginRenter(@Valid @RequestBody UserDTOLogin reqDTO){
//        User matchedUser = userService.login(reqDTO.getPhone(),reqDTO.getPassword());
//        if (matchedUser != null){
//            UserDTOFull resDTO = modelMapper.map(matchedUser, UserDTOFull.class);
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new ApiSuccess(resDTO, "Login successfully!"));
//        }else{
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(new ApiSuccess( "Invalid phone or password", false));
//        }
//    }

}
