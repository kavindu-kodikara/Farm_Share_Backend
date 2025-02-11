package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stock_price")
public class StockPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "date",nullable = false)
    private Date date;

    @Column(name = "price",nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "farm_id",nullable = false)
    private Farm farm;

    public StockPrice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
