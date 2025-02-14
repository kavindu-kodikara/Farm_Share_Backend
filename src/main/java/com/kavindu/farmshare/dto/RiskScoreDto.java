package com.kavindu.farmshare.dto;

import java.io.Serializable;

public class RiskScoreDto implements Serializable {

    private double riskScore;
    private double weatherScore;
    private double soilScore;

    public RiskScoreDto() {
    }

    public RiskScoreDto(double riskScore, double weatherScore, double soilScore) {
        this.riskScore = riskScore;
        this.weatherScore = weatherScore;
        this.soilScore = soilScore;
    }

    public double getRiskScore() { return riskScore; }
    public double getWeatherScore() { return weatherScore; }
    public double getSoilScore() { return soilScore; }
}
