package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.ResponseDto;
import com.kavindu.farmshare.dto.UserDto;
import com.kavindu.farmshare.model.Validation;
import com.kavindu.farmshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/sign-up")
    public ResponseDto signIn(@RequestBody UserDto userDto){

        ResponseDto responseDto = new ResponseDto();

        if (!userDto.getMobile().isBlank()
                && !userDto.getFname().isBlank()
                && !userDto.getLname().isBlank()
                && !userDto.getPassword().isBlank()
                && !userDto.getRePassword().isBlank()){

            if (!Validation.mobileValidation(userDto.getMobile())){
                responseDto.setMessage("Invalid mobile number");
            } else if (!userDto.getPassword().equals(userDto.getRePassword())) {
                responseDto.setMessage("Passwords do not match!");
            }else {

                responseDto = userService.signUp(userDto,userDto.getUserType().equals("farmer"));

            }

        }else {
            responseDto.setMessage("Please try again");
        }

//        System.out.println(responseDto.getMessage());
//        System.out.println(responseDto.isSuccess());

        return responseDto;
    }

    @PostMapping(value = "/sign-in")
    public ResponseDto farmerSignIn(@RequestBody UserDto userDto){
        ResponseDto responseDto = new ResponseDto();

        if (!userDto.getMobile().isBlank() && !userDto.getPassword().isBlank()){

            if (Validation.mobileValidation(userDto.getMobile())){

                responseDto = userService.signIn(userDto,userDto.getUserType().equals("farmer"));

            }else {
                responseDto.setMessage("Invalid mobile number");
            }

        }else {
            responseDto.setMessage("Please try again");
        }

        return responseDto;
    }

}
