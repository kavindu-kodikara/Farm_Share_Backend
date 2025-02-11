package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "soil_doc")
public class SoilDoc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "document",nullable = false)
    private String document;

    @Column(name = "date",nullable = false)
    private Date date;

    @Column(name = "ph",nullable = false)
    private String ph;

    @Column(name = "moisture_content",nullable = false)
    private String moistureContent;

    @Column(name = "organic_matter",nullable = false)
    private String organicMatter;

    @Column(name = "nutrient_level",nullable = false)
    private String nutrientLevel;

    @ManyToOne
    @JoinColumn(name = "farm_id",nullable = false)
    private Farm farm;

    public SoilDoc() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getMoistureContent() {
        return moistureContent;
    }

    public void setMoistureContent(String moistureContent) {
        this.moistureContent = moistureContent;
    }

    public String getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(String organicMatter) {
        this.organicMatter = organicMatter;
    }

    public String getNutrientLevel() {
        return nutrientLevel;
    }

    public void setNutrientLevel(String nutrientLevel) {
        this.nutrientLevel = nutrientLevel;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
