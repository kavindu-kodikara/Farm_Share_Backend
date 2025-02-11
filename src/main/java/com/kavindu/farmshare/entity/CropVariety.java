package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "crop_variety")
public class CropVariety implements Serializable {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "crop_type_id",nullable = false)
    private CropType cropType;

    @Column(name = "initial_price",nullable = false)
    private double initialPrice;

    public CropVariety() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }
}
