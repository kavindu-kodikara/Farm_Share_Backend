package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RiskScoreDto;
import org.springframework.stereotype.Service;

@Service
public class StockPriceCalculatorService {
    public double calculateStockPrice(double riskScore, double currentStockPrice) {
        double maxDecrease = 10.0; // Allow up to 10% drop in extreme cases
        double maxIncrease = 5.0;  // Allow up to 5% increase in low-risk cases

        double changeFactor;

        double stockPrice;

        if (riskScore > 150) {
            // Exponential decay for extreme risk (drops faster)
            changeFactor = Math.pow((riskScore / 200.0), 2) * maxDecrease;
            stockPrice = currentStockPrice * (1 - (changeFactor / 100));
            stockPrice = Math.max(stockPrice, currentStockPrice * 0.7); // Prevent excessive drop
        } else if (riskScore > 100) {
            // Linear drop
            changeFactor = (riskScore / 200.0) * maxDecrease;
            stockPrice = currentStockPrice * (1 - (changeFactor / 100));
            stockPrice = Math.max(stockPrice, currentStockPrice * 0.8);
        } else {
            // Price increases with low risk
            changeFactor = (1 - (riskScore / 100.0)) * maxIncrease;
            stockPrice = currentStockPrice * (1 + (changeFactor / 100));
            stockPrice = Math.min(stockPrice, currentStockPrice * 1.2); // Prevent overgrowth
        }

        // Round to 2 decimal places
        return Math.round(stockPrice * 100.0) / 100.0;
    }



}
