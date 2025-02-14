package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RiskScoreDto;
import org.springframework.stereotype.Service;

@Service
public class StockPriceCalculatorService {
    public double calculateStockPrice(double riskScore, double currentStockPrice) {
        double maxChangePercentage = 20.0; // Stock price can change by ±15%
        double changeFactor = (riskScore / 200.0) * maxChangePercentage;

        if (riskScore > 100) {

            double stockPrice = currentStockPrice * (1 - (changeFactor / 100));
            stockPrice = (double) Math.round(stockPrice * 10) / 10;

            return stockPrice;
        } else {

            double stockPrice = currentStockPrice * (1 + (changeFactor / 100));
            stockPrice = (double) Math.round(stockPrice * 10) / 10;

            return stockPrice;
        }
    }

}
