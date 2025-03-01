package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.ActiveStatus;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.FarmStatus;
import com.kavindu.farmshare.entity.StockPrice;
import com.kavindu.farmshare.repo.ActiveStatusRepo;
import com.kavindu.farmshare.repo.FarmRepo;
import com.kavindu.farmshare.repo.FarmStatusRepo;
import com.kavindu.farmshare.repo.StockPriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SearchService {

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    ActiveStatusRepo activeStatusRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    public SearchDto loadSearchFarms(){
        SearchDto searchDto = new SearchDto();

        ActiveStatus activeStatus = activeStatusRepo.findById(2).get();

        List<Farm> farmList = farmRepo.findFarmsByActiveStatusAndFarmStatus(activeStatus);
        ArrayList<InvestItemDto> itemList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Farm farm : farmList){
            InvestItemDto investItemDto = new InvestItemDto();

            List<ChartEntruDto> chartEntryList = new ArrayList<>();
            for (int i = 6; i >= 0; i-- ){
                LocalDate date = today.minusDays(i);
                StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm,date);
                double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

                ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
                chartEntryList.add(chartEntruDto);
            }

            String price ="Rs." + String.valueOf(chartEntryList.get(6).getValue());

            investItemDto.setChartData(chartEntryList);
            investItemDto.setType(farm.getCropType().getName());
            investItemDto.setTitle(farm.getCodeName());
            investItemDto.setPrice(price);
            investItemDto.setId(String.valueOf(farm.getId()));

            if(chartEntryList.get(6).getValue() <= chartEntryList.get(5).getValue()){
                investItemDto.setLost("true");
            }else{
                investItemDto.setLost("false");
            }

            itemList.add(investItemDto);

        }

        searchDto.setItemList(itemList);
        searchDto.setSuccess(true);
        return searchDto;
    }

    public SearchDto searchFarms(RequestDto requestDto){
        SearchDto searchDto = new SearchDto();

        ActiveStatus activeStatus = activeStatusRepo.findById(2).get();

        List<Farm> farmList = farmRepo.findFarmsByFilters(activeStatus, requestDto.getValue(), requestDto.getValue());
        ArrayList<InvestItemDto> itemList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Farm farm : farmList){
            InvestItemDto investItemDto = new InvestItemDto();

            List<ChartEntruDto> chartEntryList = new ArrayList<>();
            for (int i = 6; i >= 0; i-- ){
                LocalDate date = today.minusDays(i);
                StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm,date);
                double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

                ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
                chartEntryList.add(chartEntruDto);
            }

            String price ="Rs." + String.valueOf(chartEntryList.get(6).getValue());

            investItemDto.setChartData(chartEntryList);
            investItemDto.setType(farm.getCropType().getName());
            investItemDto.setTitle(farm.getCodeName());
            investItemDto.setPrice(price);
            investItemDto.setId(String.valueOf(farm.getId()));

            if(chartEntryList.get(6).getValue() < chartEntryList.get(5).getValue()){
                investItemDto.setLost("true");
            }else{
                investItemDto.setLost("false");
            }

            itemList.add(investItemDto);

        }

        searchDto.setItemList(itemList);
        searchDto.setSuccess(true);
        return searchDto;
    }

}
