package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/farm")
public class FarmController {

    @Autowired
    FarmService farmService;

    @PostMapping(value = "/add-farm")
    public ResponseDto addFarm(@RequestBody AddFarmDto addFarmDto){

        ResponseDto responseDto = new ResponseDto();

        if(!addFarmDto.getFarmType().isBlank()
                && !addFarmDto.getSoilNutrient().isBlank()
                && !addFarmDto.getSoilOrganicMatter().isBlank()
                && !addFarmDto.getSoilMoisture().isBlank()
                && !addFarmDto.getSoilPh().isBlank()
                && !addFarmDto.getOwnerNic().isBlank()
                && !addFarmDto.getDescription().isBlank()
                && !addFarmDto.getCodeName().isBlank()
                && !addFarmDto.getMinInvest().isBlank()
                && !addFarmDto.getAvgIncome().isBlank()
                && !addFarmDto.getFarmSize().isBlank()
                && !addFarmDto.getFarmName().isBlank()
                && !addFarmDto.getNicFront().isBlank()
                && !addFarmDto.getNicBack().isBlank()
                && !addFarmDto.getOwnershipFile().isBlank()
                && !addFarmDto.getAnalysisFile().isBlank()
                && !addFarmDto.getSoilReportFile().isBlank()
                && !addFarmDto.getLat().isBlank()
                && !addFarmDto.getLng().isBlank()
                && !addFarmDto.getUserId().isBlank()){

            responseDto = farmService.addFarm(addFarmDto);

        }else{
            responseDto.setMessage("Something went wrong please try again");
        }


        System.out.println(responseDto.isSuccess());
        return responseDto;

    }

    @PostMapping(value = "/save-images")
    public ResponseDto saveImages(@RequestBody ImageDto imageDto){

        ResponseDto responseDto = new ResponseDto();

        if (imageDto.getImageArray() != null && imageDto.getFarmId() != 0){

            responseDto = farmService.saveImages(imageDto);

        }else {
            responseDto.setMessage("Something went wrong");
        }

        return responseDto;

    }

    @PostMapping(value = "/load-my-farms")
    public ResponseDto loadMyFarms(@RequestBody RequestDto requestDto){

        farmService.updateStockPrice();
//        farmService.forceUpdateStockPrice();

        return farmService.loadMyFarms(requestDto);

    }

    @PostMapping(value = "/update-soil-report")
    public ResponseDto updateSoilReport(@RequestBody SoilReportDto soilReportDto){
        ResponseDto responseDto = new ResponseDto();

        responseDto = farmService.updateSoilReport(soilReportDto);

        if (responseDto.isSuccess()){
            farmService.forceUpdateStockPrice();
        }

        return responseDto;
    }

    @PostMapping(value = "/release-stock")
    public ResponseDto releaseStock(@RequestBody RequestDto requestDto){

        return farmService.releaseStock(requestDto);
    }

    @PostMapping(value = "/update-farm-status")
    public ResponseDto updateFarmStatus(@RequestBody RequestDto requestDto){
        return farmService.updateFarmStatus(requestDto);
    }

    @PostMapping(value = "/season-start")
    public ResponseDto farmSeasonStarts(@RequestBody SeasonStartDto seasonStartDto){
        ResponseDto responseDto = new ResponseDto();

        System.out.println(seasonStartDto.getStartDate());

        if(!seasonStartDto.getStartDate().isEmpty() && !seasonStartDto.getEndDate().isEmpty()){
            responseDto = farmService.farmSeasonStart(seasonStartDto);
        }else{
            responseDto.setMessage("Something went wrong try again");
        }

        return responseDto;
    }

    @PostMapping(value = "/load-risk-review")
    public RiskReviewDto loadRiskReview(@RequestBody RequestDto requestDto){
        return farmService.loadRiskReview(requestDto);
    }

}
