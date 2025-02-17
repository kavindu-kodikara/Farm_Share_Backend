package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.InvestorHomeDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.service.FarmService;
import com.kavindu.farmshare.service.InvestorHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/investor")
public class InvestorController {

    @Autowired
    InvestorHomeService investorHomeService;

    @Autowired
    FarmService farmService;

    @PostMapping(value = "/load-home")
    public InvestorHomeDto loadHome(@RequestBody RequestDto requestDto){
//        farmService.forceUpdateStockPrice();
        farmService.updateStockPrice();
        return investorHomeService.loadHome(requestDto);
    }

}
