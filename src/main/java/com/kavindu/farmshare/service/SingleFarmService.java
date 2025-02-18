package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.ChartEntruDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.SingleFarmDto;
import com.kavindu.farmshare.entity.*;
import com.kavindu.farmshare.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SingleFarmService {

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    PhotosRepo photosRepo;

    @Autowired
    SeasonRepo seasonRepo;

    @Autowired
    StockAllocationRepo stockAllocationRepo;

    @Autowired
    UserRepo userRepo;



    public SingleFarmDto loadSingleFarm(RequestDto requestDto){
        SingleFarmDto singleFarmDto = new SingleFarmDto();

        LocalDate today = LocalDate.now();
        Farm farm = farmRepo.findById(requestDto.getId()).get();
        Season season = seasonRepo.findByFarm(farm);

        String yield = new DecimalFormat("#,###").format(farm.getAvgIncome()) +" kg";

        singleFarmDto.setCodeName(farm.getCodeName());
        singleFarmDto.setFarmName(farm.getFarmName());
        singleFarmDto.setFarmType(farm.getCropType().getName());
        singleFarmDto.setAvgYield(yield);
        singleFarmDto.setFarmStatus(farm.getFarmStatus().getName());
        singleFarmDto.setLat(farm.getLocationLat());
        singleFarmDto.setLng(farm.getLocationLng());
        singleFarmDto.setLandSize(String.valueOf(farm.getSize()));
        singleFarmDto.setRiskScore(String.valueOf(farm.getRiskScore()));
        singleFarmDto.setOwnerName(farm.getUser().getFname()+" "+farm.getUser().getLname());
        singleFarmDto.setSeasonMonths(new SimpleDateFormat("MMM").format(season.getStartDate()) +" - "+new SimpleDateFormat("MMM").format(season.getEndDate()));
        singleFarmDto.setOwnerDate("Since "+new SimpleDateFormat("yyyy").format(farm.getUser().getDate()));

        String landSize = String.valueOf(farm.getSize()) +" ha";
        singleFarmDto.setLandSize(landSize);
        //load 1 week chart
        ArrayList<ChartEntruDto> weekChartEntryList = new ArrayList<>();

        for (int i = 6; i >= 0; i-- ){
            LocalDate date = today.minusDays(i);
            StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm,date);
            double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

            ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
            weekChartEntryList.add(chartEntruDto);
        }

        singleFarmDto.setDrop(weekChartEntryList.get(6).getValue() < weekChartEntryList.get(5).getValue());

        String[] parts = String.valueOf(weekChartEntryList.get(6).getValue()).split("\\.");
        singleFarmDto.setStockPrice(parts[0]);
        singleFarmDto.setStockPriceCents(parts[1]);

        String avgPrice = "Rs. "+ new DecimalFormat("#,###").format(weekChartEntryList.get(6).getValue() * farm.getAvgIncome());
        singleFarmDto.setAvgIncome(avgPrice);

        double percentage =  Math.round((((weekChartEntryList.get(6).getValue() - weekChartEntryList.get(5).getValue()) / weekChartEntryList.get(5).getValue()) * 100) * 100.0) / 100.0;
        double value = Math.round((weekChartEntryList.get(6).getValue() - weekChartEntryList.get(5).getValue()) * 100.0) / 100.0 ;

        singleFarmDto.setValuePercentage(String.valueOf(percentage)+"%");

        if (weekChartEntryList.get(6).getValue() < weekChartEntryList.get(5).getValue()){

            singleFarmDto.setValuePrice(value < 0 ? String.valueOf(value) :  "-"+String.valueOf(value));
        }else{

            singleFarmDto.setValuePrice(value < 0 ? String.valueOf(value) :  "+"+String.valueOf(value));
        }

        List<Photos> photosList = photosRepo.findAllByFarm(farm);
        List<String> farmPhotosList = new ArrayList<>();
        for (Photos photos : photosList){
            farmPhotosList.add(photos.getUrl());
        }


        //load 1 month chart
        ArrayList<ChartEntruDto> monthChartEntryList = new ArrayList<>();

        for (int i = 1; i <= today.getDayOfMonth(); i++) {
            LocalDate date = today.withDayOfMonth(i);
            StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm, date);
            double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

            ChartEntruDto chartEntruDto = new ChartEntruDto(i, price);
            monthChartEntryList.add(chartEntruDto);
        }

        //load season chart
        ArrayList<ChartEntruDto> seasonChartEntryList = new ArrayList<>();
        LocalDate seasonLocalDate = season.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long daysBetweenSeason = ChronoUnit.DAYS.between(seasonLocalDate, today);

        for (int i = 0; i <= daysBetweenSeason; i++) {
            LocalDate date = seasonLocalDate.plusDays(i);
            StockPrice stockPrice2 = stockPriceRepo.findByFarmAndDate(farm, date);
            double price = (stockPrice2 != null) ? stockPrice2.getPrice() : 0;

            ChartEntruDto chartEntruDto = new ChartEntruDto(i, price);
            seasonChartEntryList.add(chartEntruDto);
        }

        for (ChartEntruDto chartEntruDto : monthChartEntryList){
            System.out.println(chartEntruDto.getDate() +" : "+chartEntruDto.getValue());
        }

        User user = userRepo.findById(Integer.parseInt(requestDto.getValue())).get();

        StockAllocation stockAllocation = stockAllocationRepo.findTopByUserAndFarmOrderByDateDesc(user,farm);
        singleFarmDto.setInvested(false);
        if(stockAllocation != null){
            if (stockAllocation.getDate().compareTo(season.getStartDate()) >= 0 && stockAllocation.getDate().compareTo(season.getEndDate()) <= 0){
                singleFarmDto.setInvested(true);

                int stockPercentage = (int) ((double) stockAllocation.getCount() / farm.getReleasedStock() * 100);

                LocalDate localDate = stockAllocation.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                StockPrice stockPrice = stockPriceRepo.findByFarmAndDate(farm,localDate);

                String investedStock = "S "+stockAllocation.getCount();
                String stockPercentageTxt = String.valueOf(stockPercentage)+"% of Shares";
                String expIncome = "Rs. "+ new DecimalFormat("#,###").format(stockAllocation.getCount() * weekChartEntryList.get(6).getValue());

                singleFarmDto.setInvestedStock(investedStock);
                singleFarmDto.setInvestedPercentage(stockPercentageTxt);
                singleFarmDto.setExpectIncome(expIncome);
                singleFarmDto.setInvestDrop(stockAllocation.getCount() * weekChartEntryList.get(6).getValue() < stockAllocation.getCount() * stockPrice.getPrice());
            }
        }

        if (farm.getUser().getProfilePic() != null){
            singleFarmDto.setProfileImg(farm.getUser().getProfilePic());
        }else{
            singleFarmDto.setProfileImg(null);
        }


        singleFarmDto.setSeasonChartData(seasonChartEntryList);
        singleFarmDto.setMonthChartData(monthChartEntryList);
        singleFarmDto.setImageList(farmPhotosList);
        singleFarmDto.setWeekChartData(weekChartEntryList);
        singleFarmDto.setSuccess(true);
        return singleFarmDto;
    }
}
