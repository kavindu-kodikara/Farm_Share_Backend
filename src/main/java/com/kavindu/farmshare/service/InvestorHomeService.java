package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.*;
import com.kavindu.farmshare.model.FarmPercentage;
import com.kavindu.farmshare.model.HotItemBean;
import com.kavindu.farmshare.model.PayoutItem;
import com.kavindu.farmshare.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InvestorHomeService {

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    ActiveStatusRepo activeStatusRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    StockAllocationRepo stockAllocationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SeasonRepo seasonRepo;


    public InvestorHomeDto loadHome(RequestDto requestDto){
        InvestorHomeDto investorHomeDto = new InvestorHomeDto();

        User user = userRepo.findById(requestDto.getId()).get();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();
        ActiveStatus activeStatus = activeStatusRepo.findById(2).get();

        //search hot list
        ArrayList<HotItemBean> hotFarmList = new ArrayList<>();
        List<Farm> farmList = farmRepo.findFarmsByActiveStatusAndFarmStatus(activeStatus);
        List<FarmPercentage> farmPercentages = new ArrayList<>();

        for (Farm farm : farmList){
            StockPrice stPriceYesterday = stockPriceRepo.findByFarmAndDate(farm,yesterday);
            StockPrice stPricetoday = stockPriceRepo.findByFarmAndDate(farm,today);

            double yesterdayPrice = stPriceYesterday != null ? stPriceYesterday.getPrice() : 0;

            if(stPricetoday.getPrice() > yesterdayPrice){
                double percentage = 0;
                if (yesterdayPrice > 0){
                    percentage =  Math.round((((stPricetoday.getPrice() - yesterdayPrice) / yesterdayPrice) * 100) * 100.0) / 100.0;
                }


                farmPercentages.add(new FarmPercentage(farm, percentage));
            }

        }

        farmPercentages.sort((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()));

        int topCount = Math.min(farmPercentages.size(), 5);

        List<FarmPercentage> topFarms = farmPercentages.subList(0, topCount);

        for (FarmPercentage farmPercentage : topFarms) {
            HotItemBean hotItemBean = new HotItemBean();
            hotItemBean.setTitle(farmPercentage.getFarm().getCodeName());
            hotItemBean.setCropType(farmPercentage.getFarm().getCropType().getName());
            hotItemBean.setValue("+"+farmPercentage.getPercentage()+"%");
            hotItemBean.setId(String.valueOf(farmPercentage.getFarm().getId()));
            hotItemBean.setLost(false);

            hotFarmList.add(hotItemBean);
        }

        //load popular items
        List<Farm> popularFarms = stockAllocationRepo.findTopFarmsByStockCountAndStatus(activeStatus,PageRequest.of(0, 3));

        ArrayList<InvestItemDto> popularItemList = new ArrayList<>();

        for (Farm farm : popularFarms){

            ArrayList<ChartEntruDto> chartEntryList = new ArrayList<>();

            for (int i = 6; i >= 0; i-- ){
                LocalDate date = today.minusDays(i);
                StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(farm,date);
                double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

                ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
                chartEntryList.add(chartEntruDto);
                System.out.println(farm.getCodeName());
                System.out.println(date +" : "+price);
            }

            double percentage =  Math.round((((chartEntryList.get(6).getValue() - chartEntryList.get(5).getValue()) / chartEntryList.get(5).getValue()) * 100) * 100.0) / 100.0;
            farmPercentages.add(new FarmPercentage(farm, percentage));

            InvestItemDto investItemDto = new InvestItemDto();

            investItemDto.setId(String.valueOf(farm.getId()));
            investItemDto.setType(farm.getCropType().getName());
            investItemDto.setTitle(farm.getCodeName());
            investItemDto.setPrice(String.valueOf(chartEntryList.get(6).getValue()));

            if (chartEntryList.get(6).getValue() < chartEntryList.get(5).getValue()){
                investItemDto.setLost("true");
                investItemDto.setValue(percentage < 0 ? String.valueOf(percentage)+"%" :  "-"+String.valueOf(percentage)+"%");
            }else{
                investItemDto.setLost("false");
                investItemDto.setValue(percentage < 0 ? String.valueOf(percentage)+"%" :  "+"+String.valueOf(percentage)+"%");
            }

            investItemDto.setChartData(chartEntryList);

            popularItemList.add(investItemDto);

        }


        //load stock holding
        List<StockAllocation> stockAllocations = stockAllocationRepo.findAllByUser(user);
        ArrayList<InvestItemDto> stockHoldingList = new ArrayList<>();

        ArrayList<StockAllocationDto> allocationList = new ArrayList<>();
        double allocationTot = 0;

        ArrayList<PayoutItem> payoutList = new ArrayList<>();

        for (StockAllocation stockAllocation : stockAllocations){

            ArrayList<ChartEntruDto> chartEntryList = new ArrayList<>();

            for (int i = 6; i >= 0; i-- ){
                LocalDate date = today.minusDays(i);
                StockPrice stockPrice1 = stockPriceRepo.findByFarmAndDate(stockAllocation.getFarm(),date);
                double price = stockPrice1 != null ? stockPrice1.getPrice() : 0;

                ChartEntruDto chartEntruDto = new ChartEntruDto(date.getDayOfMonth(),price);
                chartEntryList.add(chartEntruDto);
            }

            double percentage =  Math.round((((chartEntryList.get(6).getValue() - chartEntryList.get(5).getValue()) / chartEntryList.get(5).getValue()) * 100) * 100.0) / 100.0;
            farmPercentages.add(new FarmPercentage(stockAllocation.getFarm(), percentage));

            InvestItemDto investItemDto = new InvestItemDto();

            investItemDto.setId(String.valueOf(stockAllocation.getFarm().getId()));
            investItemDto.setType(stockAllocation.getFarm().getCropType().getName());
            investItemDto.setTitle(stockAllocation.getFarm().getFarmName());

            String price = "Rs. "+ new DecimalFormat("#,###").format(chartEntryList.get(6).getValue() * stockAllocation.getCount());
            investItemDto.setPrice(price);

            if (chartEntryList.get(6).getValue() < chartEntryList.get(5).getValue()){
                investItemDto.setLost("true");
                investItemDto.setValue(percentage < 0 ? String.valueOf(percentage)+"%" :  "-"+String.valueOf(percentage)+"%");
            }else{
                investItemDto.setLost("false");
                investItemDto.setValue(percentage < 0 ? String.valueOf(percentage)+"%" :  "+"+String.valueOf(percentage)+"%");
            }

            investItemDto.setChartData(chartEntryList);
            stockHoldingList.add(investItemDto);


            //load stock allocation
            StockAllocationDto stockAllocationDto = new StockAllocationDto();
            stockAllocationDto.setName(stockAllocation.getFarm().getCodeName());
            stockAllocationDto.setValue(Math.round((stockAllocation.getCount() * chartEntryList.get(6).getValue()) * 100.0) / 100.0 );
            allocationTot += stockAllocationDto.getValue();

            allocationList.add(stockAllocationDto);

            //load payouts
            PayoutItem payoutItem = new PayoutItem();
            payoutItem.setReturnType(stockAllocation.getReturnType().getName());
            payoutItem.setTitle(stockAllocation.getFarm().getCodeName());

            Season season = seasonRepo.findTopByFarm(stockAllocation.getFarm());

            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = formatter.format(season.getEndDate());

            payoutItem.setDate(formattedDate);

            if(stockAllocation.getReturnType().getName().equals("Cash")){
                payoutItem.setPrice(price);
            }else if(stockAllocation.getReturnType().getName().equals("Crop")){

                LocalDate localDate = stockAllocation.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                double initialPrice = stockPriceRepo.findByFarmAndDate(stockAllocation.getFarm(),localDate).getPrice();

                double newStockCount = (chartEntryList.get(6).getValue() / initialPrice) *  stockAllocation.getCount();

                String cropoutput = new DecimalFormat("#,###").format(newStockCount)+" kg";

                payoutItem.setPrice(cropoutput);

            }

            payoutList.add(payoutItem);


        }



        investorHomeDto.setPayoutItemList(payoutList);
        investorHomeDto.setAllocationList(allocationList);
        investorHomeDto.setAllocationTot(allocationTot);
        investorHomeDto.setStockHoldingList(stockHoldingList);
        investorHomeDto.setPopularList(popularItemList);
        investorHomeDto.setHotList(hotFarmList);

        investorHomeDto.setSuccess(true);
        return investorHomeDto;
    }

}
