package com.kavindu.farmshare.dto;

import java.io.Serializable;

public class RiskDataDto implements Serializable {
    private String cropType;
    private double temperature;
    private double windSpeed;
    private double precipitation;
    private double humidity;
    private double soilPh;
    private double moisture;
    private double organicMatter;
    private double nutrientLevel;

    public RiskDataDto() {
    }

    public RiskDataDto(String cropType, double temperature, double windSpeed, double precipitation, double humidity, double soilPh, double moisture, double organicMatter, double nutrientLevel) {
        this.cropType = cropType;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.soilPh = soilPh;
        this.moisture = moisture;
        this.organicMatter = organicMatter;
        this.nutrientLevel = nutrientLevel;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(double soilPh) {
        this.soilPh = soilPh;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
    }

    public double getNutrientLevel() {
        return nutrientLevel;
    }

    public void setNutrientLevel(double nutrientLevel) {
        this.nutrientLevel = nutrientLevel;
    }
}
