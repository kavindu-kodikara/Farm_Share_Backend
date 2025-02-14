package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepo  extends JpaRepository<StockPrice,Integer> {
}
