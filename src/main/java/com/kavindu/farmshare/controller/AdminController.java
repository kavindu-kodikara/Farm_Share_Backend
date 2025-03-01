package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.model.Validation;
import com.kavindu.farmshare.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping(value = "/sign-in")
    public ResponseDto signIn(HttpSession session, @RequestBody UserDto userDto){
        ResponseDto responseDto = new ResponseDto();

        if (!userDto.getMobile().isBlank() && !userDto.getPassword().isBlank()){

            if (Validation.mobileValidation(userDto.getMobile())){

            responseDto = adminService.signIn(session,userDto);

            }else {
                responseDto.setMessage("Invalid mobile number");
            }

        }else {
            responseDto.setMessage("Please check your mobile or password");
        }

        return responseDto;
    }

    @PostMapping(value = "/load-pending-farms")
    public AdminFarmDto loadPendingFarms(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        adminFarmDto = adminService.loadPendingFarms();

        return adminFarmDto;
    }

    @PostMapping(value = "/update-status")
    public ResponseDto updateStatus(@RequestBody RequestDto requestDto){

        return adminService.updateStatus(requestDto);
    }

    @PostMapping(value = "/load-farms")
    public AdminFarmDto loadFarms(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        adminFarmDto = adminService.loadFarms();

        return adminFarmDto;
    }

    @PostMapping(value = "/load-users")
    public AdminUserDto loadUsers(){

       return adminService.loadUsers();

    }

    @PostMapping(value = "/update-user-status")
    public ResponseDto updateUserStatus(@RequestBody RequestDto requestDto){

        return adminService.updateUserStatus(requestDto);
    }

    @PostMapping(value = "/load-payments")
    public AdminFarmDto loadPayments(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        adminFarmDto = adminService.loadPayment();

        return adminFarmDto;
    }

    @PostMapping(value = "/update-farm-payment")
    public ResponseDto updatefarmPayment(@RequestBody RequestDto requestDto){

        return adminService.updatefarmPayment(requestDto);
    }
}
