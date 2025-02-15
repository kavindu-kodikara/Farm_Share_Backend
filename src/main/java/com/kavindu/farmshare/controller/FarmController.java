package com.kavindu.farmshare.controller;

import com.kavindu.farmshare.dto.AddFarmDto;
import com.kavindu.farmshare.dto.ImageDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.ResponseDto;
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

        return farmService.loadMyFarms(requestDto);

    }

}
