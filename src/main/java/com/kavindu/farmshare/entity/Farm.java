package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "farm")
public class Farm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "farm_name",nullable = false)
    private String farmName;

    @Column(name = "size",nullable = false)
    private double size;

    @Column(name = "avg_income",nullable = false)
    private double avgIncome;

    @Column(name = "min_investt",nullable = false)
    private int minInvest;

    @Column(name = "code_name",nullable = false)
    private String codeName;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "location_lat",nullable = false)
    private String locationLat;

    @Column(name = "location_lng",nullable = false)
    private String locationLng;

    @Column(name = "risk_score")
    private double riskScore;

    @Column(name = "tot_stock",nullable = false)
    private int totStock;

    @Column(name = "relesed_stock")
    private int releasedStock;

    @Column(name = "stock_progress")
    private int stockProgress;


    @ManyToOne
    @JoinColumn(name = "crop_type_id",nullable = false)
    private CropType cropType;

    @ManyToOne
    @JoinColumn(name = "active_status_id",nullable = false)
    private ActiveStatus activeStatus;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "farm_status_id",nullable = false)
    private FarmStatus farmStatus;

    @OneToOne
    @JoinColumn(name = "documents_id",nullable = false)
    private Documents documents;

    private String soilDocUrl;

    public Farm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getAvgIncome() {
        return avgIncome;
    }

    public void setAvgIncome(double avgIncome) {
        this.avgIncome = avgIncome;
    }

    public int getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(int minInvest) {
        this.minInvest = minInvest;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public int getTotStock() {
        return totStock;
    }

    public void setTotStock(int totStock) {
        this.totStock = totStock;
    }

    public int getReleasedStock() {
        return releasedStock;
    }

    public void setReleasedStock(int releasedStock) {
        this.releasedStock = releasedStock;
    }

    public int getStockProgress() {
        return stockProgress;
    }

    public void setStockProgress(int stockProgress) {
        this.stockProgress = stockProgress;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FarmStatus getFarmStatus() {
        return farmStatus;
    }

    public void setFarmStatus(FarmStatus farmStatus) {
        this.farmStatus = farmStatus;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public String getSoilDocUrl() {
        return soilDocUrl;
    }

    public void setSoilDocUrl(String soilDocUrl) {
        this.soilDocUrl = soilDocUrl;
    }
}
