package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.ActiveStatus;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockAllocation;
import com.kavindu.farmshare.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockAllocationRepo  extends JpaRepository<StockAllocation,Integer> {
    public List<StockAllocation> findAllByFarm(Farm farm);

    @Query("SELECT sa.farm FROM StockAllocation sa " +
            "WHERE sa.farm.activeStatus = :activeStatus " +
            "GROUP BY sa.farm " +
            "ORDER BY SUM(sa.count) DESC")
    public List<Farm> findTopFarmsByStockCountAndStatus(@Param("activeStatus") ActiveStatus activeStatus, Pageable pageable);

    public List<StockAllocation> findAllByUser(User user);
}
