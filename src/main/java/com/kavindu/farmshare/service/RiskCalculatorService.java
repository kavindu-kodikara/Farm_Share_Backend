package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RiskDataDto;
import com.kavindu.farmshare.dto.RiskScoreDto;
import org.springframework.stereotype.Service;

@Service
public class RiskCalculatorService {
    public RiskScoreDto calculateRiskScore(RiskDataDto riskDataDto) {

        String cropType  = riskDataDto.getCropType();
        double temperature = riskDataDto.getTemperature();
        double windSpeed = riskDataDto.getWindSpeed();
        double precipitation = riskDataDto.getPrecipitation();
        double humidity = riskDataDto.getHumidity();
        double soilPh = riskDataDto.getSoilPh();
        double moisture = riskDataDto.getMoisture();
        double organicMatter = riskDataDto.getOrganicMatter();
        double nutrientLevel = riskDataDto.getNutrientLevel();

        double tempMin, tempMax, windMax, precMin, precMax, humMin, humMax;
        double phMin, phMax, moistureMin, moistureMax, organicMin, organicMax;

        if (cropType.equalsIgnoreCase("rice")) {
            tempMin = 15; tempMax = 35;
            windMax = 20;
            precMin = 100; precMax = 300;
            humMin = 55; humMax = 90;
            phMin = 5.5; phMax = 7.0;
            moistureMin = 50; moistureMax = 80;
            organicMin = 2; organicMax = 5;
        } else if (cropType.equalsIgnoreCase("corn")) {
            tempMin = 15; tempMax = 30;
            windMax = 25;
            precMin = 50; precMax = 200;
            humMin = 50; humMax = 80;
            phMin = 5.8; phMax = 7.2;
            moistureMin = 30; moistureMax = 60;
            organicMin = 1.5; organicMax = 4;
        } else {
            throw new IllegalArgumentException("Unsupported crop type: " + cropType);
        }

        // Updated Weights (Total = 100%)
        double tempWeight = 30, windWeight = 25, precWeight = 25, humWeight = 20;
        double phWeight = 25, moistureWeight = 30, organicWeight = 25, nutrientWeight = 20;

        // Risk Scores**
        double weatherRisk = 0;
        double soilRisk = 0;

        // Calculate Weighted Weather Risk (0-100)
        weatherRisk += getDeviationRisk(temperature, tempMin, tempMax) * (tempWeight / 100);
        weatherRisk += getDeviationRisk(windSpeed, 0, windMax) * (windWeight / 100);
        weatherRisk += getDeviationRisk(precipitation, precMin, precMax) * (precWeight / 100);
        weatherRisk += getDeviationRisk(humidity, humMin, humMax) * (humWeight / 100);

        // Calculate Weighted Soil Risk (0-100)
        soilRisk += getDeviationRisk(soilPh, phMin, phMax) * (phWeight / 100);
        soilRisk += getDeviationRisk(moisture, moistureMin, moistureMax) * (moistureWeight / 100);
        soilRisk += getDeviationRisk(organicMatter, organicMin, organicMax) * (organicWeight / 100);

        // Nutrient Levels**
        double idealNutrientLevel = 70; // Assume 70+ is ideal
        soilRisk += getDeviationRisk(nutrientLevel, idealNutrientLevel, 100) * (nutrientWeight / 100);

        // Normalize (0-200 scale)
        weatherRisk = Math.min(weatherRisk * 200, 200);
        soilRisk = Math.min(soilRisk * 200, 200);

        // Final Risk Score (Weather 60% + Soil 40%)
        double totalRisk = (weatherRisk * 0.6) + (soilRisk * 0.4);
        totalRisk = Math.min(totalRisk, 200);

        //Apply Dynamic Minimum Risk Thresholds
        double minRiskThreshold = (totalRisk < 10) ? 30 : (totalRisk < 20) ? 25 : 20;
        totalRisk = Math.max(totalRisk, minRiskThreshold);
        weatherRisk = Math.max(weatherRisk, minRiskThreshold * 0.6);
        soilRisk = Math.max(soilRisk, minRiskThreshold * 0.4);

        totalRisk = (double) Math.round(totalRisk * 10) / 10;
        weatherRisk = (double) Math.round(weatherRisk * 10) / 10;
        soilRisk = (double) Math.round(soilRisk * 10) / 10;

        return new RiskScoreDto(totalRisk, weatherRisk, soilRisk);
    }

    private double getDeviationRisk(double value, double min, double max) {
        if (value >= min && value <= max) return 0;
        double deviation = (value < min) ? (min - value) / min : (value - max) / max;
        return deviation;
    }



}
