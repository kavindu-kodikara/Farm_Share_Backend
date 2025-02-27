package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RiskScoreDto;
import org.springframework.stereotype.Service;

@Service
public class StockPriceCalculatorService {
    public double calculateStockPrice(double riskScore, double currentStockPrice) {
        double maxDecrease = 10.0; // Allow up to 10% drop extreme cases
        double maxIncrease = 5.0;  // Allow up to 5% increase low-risk cases

        double changeFactor;
        double stockPrice;

        double minStockPrice = 25.0; // limit

        if (riskScore > 150) {
            changeFactor = Math.pow((riskScore / 200.0), 2) * maxDecrease;
            stockPrice = currentStockPrice * (1 - (changeFactor / 100));
            stockPrice = Math.max(stockPrice, minStockPrice); 
        } else if (riskScore > 100) {
            changeFactor = (riskScore / 200.0) * maxDecrease;
            stockPrice = currentStockPrice * (1 - (changeFactor / 100));
            stockPrice = Math.max(stockPrice, minStockPrice);
        } else {
            changeFactor = (1 - (riskScore / 100.0)) * maxIncrease;
            stockPrice = currentStockPrice * (1 + (changeFactor / 100));
            stockPrice = Math.min(stockPrice, currentStockPrice * 1.2);
        }

        // Round 2 decimal
        return Math.round(stockPrice * 100.0) / 100.0;
    }



}
