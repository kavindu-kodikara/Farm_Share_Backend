package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.StockAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockAllocationRepo  extends JpaRepository<StockAllocation,Integer> {
}
