package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockAllocationRepo  extends JpaRepository<StockAllocation,Integer> {
    public List<StockAllocation> findAllByFarm(Farm farm);
}
