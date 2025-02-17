package com.kavindu.farmshare.model;

import com.kavindu.farmshare.entity.Farm;

public class FarmPercentage {
    Farm farm;
    double percentage;

    public FarmPercentage(Farm farm, double percentage) {
        this.farm = farm;
        this.percentage = percentage;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
