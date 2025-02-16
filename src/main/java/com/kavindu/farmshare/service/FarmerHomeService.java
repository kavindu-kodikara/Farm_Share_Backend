package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockAllocation;
import com.kavindu.farmshare.entity.StockPrice;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.repo.FarmRepo;
import com.kavindu.farmshare.repo.StockAllocationRepo;
import com.kavindu.farmshare.repo.StockPriceRepo;
import com.kavindu.farmshare.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FarmerHomeService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    StockAllocationRepo stockAllocationRepo;

    public FarmerHomeDto loadHome(RequestDto requestDto){

        User user = userRepo.findById(requestDto.getId()).get();

        List<Farm> farmList = farmRepo.findAllByUser(user);

        ArrayList<NameIdDto> chipArrayList = new ArrayList<>();

        for (Farm farm : farmList){
            NameIdDto nameIdDto = new NameIdDto();
            nameIdDto.setName(farm.getFarmName());
            nameIdDto.setId(farm.getId());

            chipArrayList.add(nameIdDto);
        }

        RequestDto farmIdDto = new RequestDto(farmList.get(0).getId());

        FarmerHomeDto farmerHomeDto = loadHomeData(farmIdDto);

        farmerHomeDto.setChipArray(chipArrayList);
        farmerHomeDto.setSuccess(true);
        return farmerHomeDto;
    }

    public FarmerHomeDto loadHomeData(RequestDto requestDto){
        FarmerHomeDto farmerHomeDto = new FarmerHomeDto();

        Farm farm = farmRepo.findById(requestDto.getId()).get();
        StockPrice stockPrice = stockPriceRepo.findTopByFarmOrderByDateDesc(farm);

        farmerHomeDto.setRiskScore(farm.getRiskScore());
        farmerHomeDto.setCropType(farm.getCropType().getName());
        farmerHomeDto.setFarmName(farm.getFarmName());
        farmerHomeDto.setTotStock(farm.getTotStock());
        farmerHomeDto.setRelesedStock(farm.getReleasedStock());
        farmerHomeDto.setStockProgress(farm.getStockProgress());
        farmerHomeDto.setExpectIncome(farm.getTotStock() * stockPrice.getPrice());
        farmerHomeDto.setFarmId(farm.getId());
        farmerHomeDto.setFarmStatus(farm.getFarmStatus().getName());

        LocalDate today = LocalDate.now();

        //load chart data
        ArrayList<ChartEntruDto> chartEntryList = new ArrayList<>();

        for (int i = 6; i >= 0; i-- ){
            LocalDate date = today.minusDays(i);
            StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm,date);
            double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

            ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
            chartEntryList.add(chartEntruDto);
        }

        farmerHomeDto.setChartEntryList(chartEntryList);
        farmerHomeDto.setPriceDrop(chartEntryList.get(6).getValue() <= chartEntryList.get(5).getValue());

        //load stock allocation
        List<StockAllocationTableItemDto> tableItemList = new ArrayList<>();

        List<StockAllocation> stockAllocations = stockAllocationRepo.findAllByFarm(farm);

        for (StockAllocation stockAllocation : stockAllocations){
            StockAllocationTableItemDto stockTable = new StockAllocationTableItemDto();
            stockTable.setProfileUrl(stockAllocation.getUser().getProfilePic());
            stockTable.setName(stockAllocation.getUser().getFname()+" "+stockAllocation.getUser().getLname());
            stockTable.setAmount(String.valueOf(stockAllocation.getCount() * stockPrice.getPrice()));
            stockTable.setStock(String.valueOf(stockAllocation.getCount()));

            tableItemList.add(stockTable);
        }

        farmerHomeDto.setInvestorsCount(String.valueOf(tableItemList.size())+" Total");
        farmerHomeDto.setTableItemList(tableItemList);

        return farmerHomeDto;
    }

}
