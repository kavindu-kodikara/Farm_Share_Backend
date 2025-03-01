package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.service.*;
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
    SingleFarmService singleFarmService;

    @Autowired
    FarmService farmService;

    @Autowired
    SearchService searchService;

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/load-home")
    public InvestorHomeDto loadHome(@RequestBody RequestDto requestDto){
//        farmService.forceUpdateStockPrice();
        farmService.updateStockPrice();
        return investorHomeService.loadHome(requestDto);
    }

    @PostMapping(value = "/load-single-farm")
    public SingleFarmDto loadSingleFarm(@RequestBody RequestDto requestDto){
        return singleFarmService.loadSingleFarm(requestDto);
    }

    @PostMapping(value = "/load-search-farms")
    public SearchDto loadSearchFarm(){
        return searchService.loadSearchFarms();
    }

    @PostMapping(value = "/search-farms")
    public SearchDto searchFarm(@RequestBody RequestDto requestDto){
        return searchService.searchFarms(requestDto);
    }

    @PostMapping(value = "/load-transaction")
    public TransactionDto loadTransaction(@RequestBody RequestDto requestDto){
        return transactionService.loadTransaction(requestDto);
    }


}
