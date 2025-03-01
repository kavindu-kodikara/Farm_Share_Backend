package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.PaymentDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.ResponseDto;
import com.kavindu.farmshare.dto.StockPageLoadDto;
import com.kavindu.farmshare.entity.*;
import com.kavindu.farmshare.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class StockSearvice {

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    StockAllocationRepo stockAllocationRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SeasonRepo seasonRepo;

    @Autowired
    ReturnTypeRepo returnTypeRepo;

    @Autowired
    TransactionTypeRepo transactionTypeRepo;

    public StockPageLoadDto loadStockPage(RequestDto requestDto){
        StockPageLoadDto stockPageLoadDto = new StockPageLoadDto();

        Farm farm = farmRepo.findById(requestDto.getId()).get();
        StockPrice stockPrice = stockPriceRepo.findTopByFarmOrderByDateDesc(farm);

        stockPageLoadDto.setFarmName(farm.getFarmName());
        stockPageLoadDto.setCodeName(farm.getCodeName());
        stockPageLoadDto.setType(farm.getCropType().getName());
        stockPageLoadDto.setMinCount(farm.getMinInvest());
        stockPageLoadDto.setAvailableCount(farm.getReleasedStock() - farm.getStockProgress());
        stockPageLoadDto.setOwnerId(farm.getUser().getId());
        stockPageLoadDto.setStockPrice(stockPrice.getPrice());

        stockPageLoadDto.setSuccess(true);
        return stockPageLoadDto;
    }

    public ResponseDto makePayment(PaymentDto paymentDto){

        Farm farm = farmRepo.findById(paymentDto.getFarmId()).get();
        User user = userRepo.findById(paymentDto.getUserId()).get();
        Season season = seasonRepo.findTopByFarm(farm);
        ReturnType returnType = returnTypeRepo.findByName(paymentDto.getReturnType());

        TransactionType transactionType = transactionTypeRepo.findById(2).get();

        StockAllocation stockAllocation = stockAllocationRepo.findTopByUserAndFarmOrderByDateDesc(user,farm);

        ResponseDto responseDto = new ResponseDto();

        if (stockAllocation != null){
            if (stockAllocation.getDate().compareTo(season.getStartDate()) <= 0 && stockAllocation.getDate().compareTo(season.getEndDate()) >= 0){
                StockAllocation newStockAllocation = new StockAllocation();
                newStockAllocation.setCount(paymentDto.getStockCount());
                newStockAllocation.setDate(new Date());
                newStockAllocation.setFarm(farm);
                newStockAllocation.setUser(user);
                newStockAllocation.setReturnType(returnType);

                Transaction transaction = new Transaction();
                transaction.setPrice(paymentDto.getPrice());
                transaction.setFromUser(user);
                transaction.setToUser(farm.getUser());
                transaction.setTime(new Date());
                transaction.setTitle(farm.getFarmName());
                transaction.setTransactionType(transactionType);

                farm.setStockProgress(farm.getStockProgress() + paymentDto.getStockCount());

                farmRepo.save(farm);
                transactionRepo.save(transaction);
                stockAllocationRepo.save(newStockAllocation);
            }
        }else{
            StockAllocation newStockAllocation = new StockAllocation();
            newStockAllocation.setCount(paymentDto.getStockCount());
            newStockAllocation.setDate(new Date());
            newStockAllocation.setFarm(farm);
            newStockAllocation.setUser(user);
            newStockAllocation.setReturnType(returnType);

            Transaction transaction = new Transaction();
            transaction.setPrice(paymentDto.getPrice());
            transaction.setFromUser(user);
            transaction.setToUser(farm.getUser());
            transaction.setTime(new Date());
            transaction.setTitle(farm.getFarmName());
            transaction.setTransactionType(transactionType);

            farm.setStockProgress(farm.getStockProgress() + paymentDto.getStockCount());

            farmRepo.save(farm);
            transactionRepo.save(transaction);
            stockAllocationRepo.save(newStockAllocation);
        }

        responseDto.setSuccess(true);

        return responseDto;

    }



}
