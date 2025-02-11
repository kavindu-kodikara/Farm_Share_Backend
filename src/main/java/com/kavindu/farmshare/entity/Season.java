package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "season")
public class Season implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_date",nullable = false)
    private Date startDate;

    @Column(name = "end_date",nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "farm_id",nullable = false)
    private Farm farm;

    public Season() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
