package com.kavindu.farmshare.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "stock_allocation")
public class StockAllocation implements Serializable {

    @Id
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "count",nullable = false)
    private int count;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "farm_id",nullable = false)
    private Farm farm;

    @ManyToOne
    @JoinColumn(name = "return_type_id",nullable = false)
    private ReturnType returnType;

    public StockAllocation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }
}
