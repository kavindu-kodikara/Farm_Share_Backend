package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/farmer")
public class FarmerController {

    @Autowired
    FarmerService farmerService;

    @PostMapping(value = "/sign-up")
    public void signIn(){
        System.out.println("sign-up");
    }

}
