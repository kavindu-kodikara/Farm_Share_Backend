package com.kavindu.farmshare.dto;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockAllocation;

import java.io.Serializable;
import java.util.List;

public class AdminPaymentDto implements Serializable {
    private Farm farm;
    private List<StockAllocation> stockAllocationList;
    private boolean success;
    private double price;
    private  double payment;

    public AdminPaymentDto() {
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public List<StockAllocation> getStockAllocationList() {
        return stockAllocationList;
    }

    public void setStockAllocationList(List<StockAllocation> stockAllocationList) {
        this.stockAllocationList = stockAllocationList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
