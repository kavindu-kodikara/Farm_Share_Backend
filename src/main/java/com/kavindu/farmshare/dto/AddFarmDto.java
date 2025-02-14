package com.kavindu.farmshare.dto;

import java.io.Serializable;

public class AddFarmDto implements Serializable {
    private String nicFront;
    private String nicBack;
    private String ownershipFile;
    private String analysisFile;
    private String soilReportFile;

    private String farmType;
    private String soilNutrient;
    private String soilOrganicMatter;
    private String soilMoisture;
    private String soilPh;
    private String ownerNic;
    private String description;
    private String codeName;
    private String minInvest;
    private String avgIncome;
    private String farmSize;
    private String farmName;
    private String userId;

    private String lat;
    private String lng;

    public AddFarmDto() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public String getSoilNutrient() {
        return soilNutrient;
    }

    public void setSoilNutrient(String soilNutrient) {
        this.soilNutrient = soilNutrient;
    }

    public String getSoilOrganicMatter() {
        return soilOrganicMatter;
    }

    public void setSoilOrganicMatter(String soilOrganicMatter) {
        this.soilOrganicMatter = soilOrganicMatter;
    }

    public String getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(String soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public String getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(String soilPh) {
        this.soilPh = soilPh;
    }

    public String getOwnerNic() {
        return ownerNic;
    }

    public void setOwnerNic(String ownerNic) {
        this.ownerNic = ownerNic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(String minInvest) {
        this.minInvest = minInvest;
    }

    public String getAvgIncome() {
        return avgIncome;
    }

    public void setAvgIncome(String avgIncome) {
        this.avgIncome = avgIncome;
    }

    public String getFarmSize() {
        return farmSize;
    }

    public void setFarmSize(String farmSize) {
        this.farmSize = farmSize;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getNicFront() {
        return nicFront;
    }

    public void setNicFront(String nicFront) {
        this.nicFront = nicFront;
    }

    public String getNicBack() {
        return nicBack;
    }

    public void setNicBack(String nicBack) {
        this.nicBack = nicBack;
    }

    public String getOwnershipFile() {
        return ownershipFile;
    }

    public void setOwnershipFile(String ownershipFile) {
        this.ownershipFile = ownershipFile;
    }

    public String getAnalysisFile() {
        return analysisFile;
    }

    public void setAnalysisFile(String analysisFile) {
        this.analysisFile = analysisFile;
    }

    public String getSoilReportFile() {
        return soilReportFile;
    }

    public void setSoilReportFile(String soilReportFile) {
        this.soilReportFile = soilReportFile;
    }
}
