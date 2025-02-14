package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.CropType;
import com.kavindu.farmshare.entity.Documents;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FarmService {

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    FarmStatusRepo farmStatusRepo;

    @Autowired
    CropTypeRepo cropTypeRepo;

    @Autowired
    DocumentsRepo documentsRepo;

    @Autowired
    ActiveStatusRepo activeStatusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SoilDocRepo soilDocRepo;

    @Autowired
    SoilScoreRepo soilScoreRepo;

    @Autowired
    WeatherScoreRepo weatherScoreRepo;

    @Autowired
    CropVarietyRepo cropVarietyRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    WetherService wetherService;

    @Autowired
    RiskCalculatorService riskCalculatorService;

    @Autowired
    StockPriceCalculatorService stockPriceCalculatorService;

    public ResponseDto addFarm(AddFarmDto addFarmDto){
        ResponseDto responseDto = new ResponseDto();

        //get weather data
        WeatherDataDto weatherDataDto = wetherService.getWeather(
                Double.parseDouble(addFarmDto.getLat()),
                Double.parseDouble(addFarmDto.getLng()));


        RiskDataDto riskDataDto = new RiskDataDto(
                addFarmDto.getFarmType(),
                weatherDataDto.getCurrent().getTemperature(),
                weatherDataDto.getCurrent().getWindSpeed(),
                weatherDataDto.getCurrent().getPrecipitation(),
                weatherDataDto.getCurrent().getHumidity(),
                Double.parseDouble(addFarmDto.getSoilPh()),
                Double.parseDouble(addFarmDto.getSoilMoisture()),
                Double.parseDouble(addFarmDto.getSoilOrganicMatter()),
                Double.parseDouble(addFarmDto.getSoilNutrient())
                );

        CropType cropType = null;
        
        if (addFarmDto.getFarmType().equals("Farm")){
            cropType = cropTypeRepo.findById(1).get();
        } else if (addFarmDto.getFarmType().equals("Corn")) {
            cropType = cropTypeRepo.findById(2).get();
        }

        if (cropType != null){

            //calculate risc and stock price
            RiskScoreDto riskScoreDto = riskCalculatorService.calculateRiskScore(riskDataDto);
            double stockPrice = stockPriceCalculatorService.calculateStockPrice(riskScoreDto.getRiskScore(),cropType.getPrice());

            //save documents
            Documents documents = new Documents();
            documents.setAnalysisDoc(addFarmDto.getAnalysisFile());
            documents.setNicBack(addFarmDto.getNicBack());
            documents.setNicFront(addFarmDto.getNicFront());
            documents.setOwnershipDoc(addFarmDto.getOwnershipFile());

            Documents savedDocuments = documentsRepo.save(documents);

            //search user
            User user = userRepo.findById(Integer.valueOf(addFarmDto.getUserId())).get();

            System.out.println(user.getFname());

            System.out.println("Risk score : "+riskScoreDto.getRiskScore());
            System.out.println("Wether score : "+riskScoreDto.getWeatherScore());
            System.out.println("Soil score : "+riskScoreDto.getSoilScore());
            System.out.println("Stock price : "+stockPrice);

        }
        responseDto.setMessage("Something went wrong please try again");

        return responseDto;
    }

}
