package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.PaymentDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.ResponseDto;
import com.kavindu.farmshare.dto.StockPageLoadDto;
import com.kavindu.farmshare.service.StockSearvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/stock")
public class BuyStockController {

    @Autowired
    StockSearvice stockSearvice;

    @PostMapping(value = "/load-page")
    public StockPageLoadDto loadStockPage(@RequestBody RequestDto requestDto){
        return stockSearvice.loadStockPage(requestDto);
    }

    @PostMapping(value = "/make-payment")
    public ResponseDto makePayment(@RequestBody PaymentDto paymentDto){
         return stockSearvice.makePayment(paymentDto);
    }
}
