package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RiskDataDto;
import com.kavindu.farmshare.dto.RiskScoreDto;
import org.springframework.stereotype.Service;

@Service
public class RiskCalculatorService {
    public RiskScoreDto calculateRiskScore(RiskDataDto riskDataDto) {
        String cropType = riskDataDto.getCropType();
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
            tempMin = 15; tempMax = 30;
            windMax = 30;
            precMin = 100; precMax = 300;
            humMin = 50; humMax = 80;
            phMin = 6; phMax = 7.0;
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

        // Adjusted Weights
        double tempWeight = 40, windWeight = 40, precWeight = 40, humWeight = 35;
        double phWeight = 40, moistureWeight = 40, organicWeight = 35, nutrientWeight = 35;

        // Weather Risk Calculation
        double weatherRisk = 0;
        weatherRisk += getDeviationRisk(temperature, tempMin, tempMax) * (tempWeight / 100);
        weatherRisk += getDeviationRisk(windSpeed, 0, windMax) * (windWeight / 100);
        weatherRisk += getDeviationRisk(precipitation, precMin, precMax) * (precWeight / 100);
        weatherRisk += getDeviationRisk(humidity, humMin, humMax) * (humWeight / 100);

        // Soil Risk Calculation
        double soilRisk = 0;
        soilRisk += getDeviationRisk(soilPh, phMin, phMax) * (phWeight / 100);
        soilRisk += getDeviationRisk(moisture, moistureMin, moistureMax) * (moistureWeight / 100);
        soilRisk += getDeviationRisk(organicMatter, organicMin, organicMax) * (organicWeight / 100);
        soilRisk += getDeviationRisk(nutrientLevel, 70, 100) * (nutrientWeight / 100);

        // Normalize risks (scaled to 0-400 range to allow higher risk scores)
        weatherRisk = Math.min(weatherRisk * 200, 150);
        soilRisk = Math.min(soilRisk * 200, 150);

        // Final Risk Score (Weather 60% + Soil 40%)
        double totalRisk = (weatherRisk * 0.6) + (soilRisk * 0.4);
        totalRisk = Math.min(totalRisk, 350);

        // Allow Extreme Risks to Exceed 170+
        if (totalRisk > 150) {
            totalRisk *= 1.3; // Apply additional penalty for extreme deviations
        }

        // Ensure risk score does not exceed 200
        totalRisk = Math.min(totalRisk, 200);

        // Set a reasonable minimum threshold
        double minRiskThreshold = 20;
        totalRisk = Math.max(totalRisk, minRiskThreshold);

        // Round to one decimal place
        totalRisk = Math.round(totalRisk * 10) / 10.0;
        weatherRisk = Math.round(weatherRisk * 10) / 10.0;
        soilRisk = Math.round(soilRisk * 10) / 10.0;

        return new RiskScoreDto(totalRisk, weatherRisk, soilRisk);
    }

    private double getDeviationRisk(double value, double min, double max) {
        if (value >= min && value <= max) {
            return 0;
        }
        return (value < min) ? ((min - value) / min * 1.5) : ((value - max) / max * 1.5);
    }
}

// ******** bench mark 110 *********
