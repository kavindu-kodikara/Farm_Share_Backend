package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.*;
import com.kavindu.farmshare.model.FarmItem;
import com.kavindu.farmshare.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    PhotosRepo photosRepo;


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

        Optional<CropType> cropOptin = null;
        
        if (addFarmDto.getFarmType().equals("Rice")){
            cropOptin = cropTypeRepo.findById(1);
        } else if (addFarmDto.getFarmType().equals("Corn")) {
            cropOptin = cropTypeRepo.findById(2);
        }

        if (cropOptin != null){

            CropType cropType = cropOptin.get();

            //calculate risc score
            RiskScoreDto riskScoreDto = riskCalculatorService.calculateRiskScore(riskDataDto);

            //calculate stock price
            double singleStockPrice = stockPriceCalculatorService.calculateStockPrice(riskScoreDto.getRiskScore(),cropType.getPrice());

            //save documents
            Documents documents = new Documents();
            documents.setAnalysisDoc(addFarmDto.getAnalysisFile());
            documents.setNicBack(addFarmDto.getNicBack());
            documents.setNicFront(addFarmDto.getNicFront());
            documents.setOwnershipDoc(addFarmDto.getOwnershipFile());

            Documents savedDocuments = documentsRepo.save(documents);

            //search user
            User user = userRepo.findById(Integer.valueOf(addFarmDto.getUserId())).get();

            //get active status
            ActiveStatus activeStatus = activeStatusRepo.findById(1).get();

            //get farm status
            FarmStatus farmStatus = farmStatusRepo.findById(6).get();

            if (!farmRepo.existsByCodeName(addFarmDto.getCodeName())){
                //save farm
                Farm farm = new Farm();
                farm.setFarmName(addFarmDto.getFarmName());
                farm.setSize(Double.parseDouble(addFarmDto.getFarmSize()));
                farm.setAvgIncome(Double.parseDouble(addFarmDto.getAvgIncome()));
                farm.setMinInvest(Integer.parseInt(addFarmDto.getMinInvest()));
                farm.setCodeName(addFarmDto.getCodeName());
                farm.setDescription(addFarmDto.getDescription());
                farm.setLocationLat(addFarmDto.getLat());
                farm.setLocationLng(addFarmDto.getLng());
                farm.setRiskScore(riskScoreDto.getRiskScore());
                farm.setTotStock(Integer.parseInt(addFarmDto.getAvgIncome()));
                farm.setCropType(cropType);
                farm.setActiveStatus(activeStatus);
                farm.setUser(user);
                farm.setFarmStatus(farmStatus);
                farm.setDocuments(savedDocuments);

                Farm savedFarm = farmRepo.save(farm);

                //save soil doc
                SoilDoc soilDoc = new SoilDoc();
                soilDoc.setDocument(addFarmDto.getSoilReportFile());
                soilDoc.setDate(new Date());
                soilDoc.setPh(addFarmDto.getSoilPh());
                soilDoc.setMoistureContent(addFarmDto.getSoilMoisture());
                soilDoc.setOrganicMatter(addFarmDto.getSoilOrganicMatter());
                soilDoc.setNutrientLevel(addFarmDto.getSoilNutrient());
                soilDoc.setFarm(savedFarm);

                SoilDoc savedSoilDoc = soilDocRepo.save(soilDoc);

                //save soil score
                SoilScore soilScore = new SoilScore();
                soilScore.setScore(riskScoreDto.getSoilScore());
                soilScore.setDate(new Date());
                soilScore.setSoilDoc(savedSoilDoc);

                soilScoreRepo.save(soilScore);

                //save stock price
                StockPrice stockPrice = new StockPrice();
                stockPrice.setDate(new Date());
                stockPrice.setPrice(singleStockPrice);
                stockPrice.setFarm(savedFarm);

                stockPriceRepo.save(stockPrice);

                //save weather score
                WeatherScore weatherScore = new WeatherScore();
                weatherScore.setScore(riskScoreDto.getWeatherScore());
                weatherScore.setDate(new Date());
                weatherScore.setFarm(savedFarm);

                weatherScoreRepo.save(weatherScore);

                responseDto.setData(savedFarm.getId());
                responseDto.setMessage("Farm added");
                responseDto.setSuccess(true);
            }else {
                responseDto.setMessage("Code name already exist");
            }



        }else{

            responseDto.setMessage("Crop type not found");

        }


        return responseDto;
    }

    public ResponseDto saveImages(ImageDto imageDto){
        ResponseDto responseDto = new ResponseDto();

        Farm farm = farmRepo.findById(imageDto.getFarmId()).get();

        ArrayList<Photos> photosArrayList = new ArrayList<>();

        for (String url : imageDto.getImageArray()){
            Photos photos = new Photos();
            photos.setFarm(farm);
            photos.setUrl(url);

            photosArrayList.add(photos);
        }

        photosRepo.saveAll(photosArrayList);

        responseDto.setMessage("Farm add successful");
        responseDto.setSuccess(true);


        return responseDto;
    }

    public ResponseDto loadMyFarms(RequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();

        User user = userRepo.findById(requestDto.getId()).get();

        if (user != null){

            ArrayList<FarmItem> farmItemList = new ArrayList<>();
             List<Farm> farmList = farmRepo.findAllByUser(user);

             for (Farm farm : farmList){
                 FarmItem farmItem = new FarmItem();
                 farmItem.setCropType(farm.getCropType().getName());
                 farmItem.setFarmName(farm.getFarmName());
                 farmItem.setStockCount(String.valueOf(farm.getStockProgress()));

                 if (farm.getRiskScore() > 110.0){
                     farmItem.setIsAtRisk("true");
                 }else{
                     farmItem.setIsAtRisk("false");
                 }
                 farmItemList.add(farmItem);
             }

             responseDto.setData(farmItemList);
             responseDto.setSuccess(true);

        }else {
            responseDto.setMessage("Please try again later");
        }

        return responseDto;
    }

    @Async
    public void updateStockPrice(){

        List<Farm> farmList = farmRepo.findFarmsWithoutStockPriceToday();

        for (Farm farm : farmList){

            System.out.println(farm.getFarmName());
            //get weather data
            WeatherDataDto weatherDataDto = wetherService.getWeather(
                    Double.parseDouble(farm.getLocationLat()),
                    Double.parseDouble(farm.getLocationLng()));

            //get soil doc
            SoilDoc soilDoc = soilDocRepo.findByFarm(farm);

            RiskDataDto riskDataDto = new RiskDataDto(
                    farm.getCropType().getName(),
                    weatherDataDto.getCurrent().getTemperature(),
                    weatherDataDto.getCurrent().getWindSpeed(),
                    weatherDataDto.getCurrent().getPrecipitation(),
                    weatherDataDto.getCurrent().getHumidity(),
                    Double.parseDouble(soilDoc.getPh()),
                    Double.parseDouble(soilDoc.getMoistureContent()),
                    Double.parseDouble(soilDoc.getOrganicMatter()),
                    Double.parseDouble(soilDoc.getNutrientLevel())
            );

            //find last stock price
            StockPrice stockPrice = stockPriceRepo.findTopByFarmOrderByDateDesc(farm);

            //calculate risc score
            RiskScoreDto riskScoreDto = riskCalculatorService.calculateRiskScore(riskDataDto);

            //calculate stock price
            double singleStockPrice = stockPriceCalculatorService.calculateStockPrice(riskScoreDto.getRiskScore(),stockPrice.getPrice());


            // update farm with new risk score
            farm.setRiskScore(riskScoreDto.getRiskScore());
            farmRepo.save(farm);

            // save new weather score
            WeatherScore weatherScore = new WeatherScore();
            weatherScore.setScore(riskScoreDto.getWeatherScore());
            weatherScore.setDate(new Date());
            weatherScore.setFarm(farm);
            weatherScoreRepo.save(weatherScore);

            //save new soil score
            SoilScore soilScore = new SoilScore();
            soilScore.setSoilDoc(soilDoc);
            soilScore.setDate(new Date());
            soilScore.setScore(riskScoreDto.getSoilScore());
            soilScoreRepo.save(soilScore);

            //save new stock price
            StockPrice newStockPrice = new StockPrice();
            newStockPrice.setDate(new Date());
            newStockPrice.setFarm(farm);
            newStockPrice.setPrice(singleStockPrice);
            stockPriceRepo.save(newStockPrice);


        }

    }

    @Async
    public void forceUpdateStockPrice(){
        List<Farm> farmList = farmRepo.findAll();

        Date today = new Date();
        LocalDate today2 = LocalDate.now();

        for (Farm farm : farmList) {

            System.out.println(farm.getFarmName());

            // Get weather data
            WeatherDataDto weatherDataDto = wetherService.getWeather(
                    Double.parseDouble(farm.getLocationLat()),
                    Double.parseDouble(farm.getLocationLng()));

            // Get soil doc
            SoilDoc soilDoc = soilDocRepo.findByFarm(farm);

            // Prepare risk data
            RiskDataDto riskDataDto = new RiskDataDto(
                    farm.getCropType().getName(),
                    weatherDataDto.getCurrent().getTemperature(),
                    weatherDataDto.getCurrent().getWindSpeed(),
                    weatherDataDto.getCurrent().getPrecipitation(),
                    weatherDataDto.getCurrent().getHumidity(),
                    Double.parseDouble(soilDoc.getPh()),
                    Double.parseDouble(soilDoc.getMoistureContent()),
                    Double.parseDouble(soilDoc.getOrganicMatter()),
                    Double.parseDouble(soilDoc.getNutrientLevel())
            );

            // Get latest stock price
            StockPrice lastStockPrice = stockPriceRepo.findTopByFarmOrderByDateDesc(farm);
            double previousPrice = (lastStockPrice != null) ? lastStockPrice.getPrice() : 130.0;

            // Calculate risk score
            RiskScoreDto riskScoreDto = riskCalculatorService.calculateRiskScore(riskDataDto);

            // Calculate new stock price
            double singleStockPrice = stockPriceCalculatorService.calculateStockPrice(riskScoreDto.getRiskScore(), previousPrice);

            //Always update farm's risk score
            farm.setRiskScore(riskScoreDto.getRiskScore());
            farmRepo.save(farm);

            //Update or insert WeatherScore
            WeatherScore weatherScore = weatherScoreRepo.findByFarmAndDate(farm, today2);

            if (weatherScore == null){ weatherScore = new WeatherScore(); }

            weatherScore.setScore(riskScoreDto.getWeatherScore());
            weatherScore.setDate(today);
            weatherScore.setFarm(farm);
            weatherScoreRepo.save(weatherScore);

            //Update or insert SoilScore
            SoilScore soilScore = soilScoreRepo.findBySoilDocAndDate(soilDoc, today2);

            if (soilScore == null){ soilScore = new SoilScore(); }

            soilScore.setSoilDoc(soilDoc);
            soilScore.setDate(today);
            soilScore.setScore(riskScoreDto.getSoilScore());
            soilScoreRepo.save(soilScore);

            //Update or insert StockPrice
            StockPrice stockPrice = stockPriceRepo.findByFarmAndDate(farm, today2);

            if (stockPrice == null){ stockPrice = new StockPrice(); }

            stockPrice.setDate(today);
            stockPrice.setFarm(farm);
            stockPrice.setPrice(singleStockPrice);
            stockPriceRepo.save(stockPrice);
        }
    }

}
