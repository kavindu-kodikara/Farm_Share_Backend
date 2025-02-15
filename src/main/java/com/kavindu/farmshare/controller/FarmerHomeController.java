package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.FarmerHomeDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.ResponseDto;
import com.kavindu.farmshare.service.FarmerHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/farmer-home")
public class FarmerHomeController {

    @Autowired
    FarmerHomeService farmerHomeService;

    @PostMapping(value = "/load-home")
    public FarmerHomeDto loadHome(@RequestBody RequestDto requestDto){

        FarmerHomeDto farmerHomeDto = new FarmerHomeDto();

        farmerHomeDto = farmerHomeService.loadHome(requestDto);

        return farmerHomeDto;

    }

}
