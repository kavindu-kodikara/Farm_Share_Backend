package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockAllocationRepo  extends JpaRepository<StockAllocation,Integer> {
    public List<StockAllocation> findAllByFarm(Farm farm);

    public List<StockAllocation> findAllByFarmAndReturnType(Farm farm, ReturnType returnType);

    @Query("SELECT sa.farm FROM StockAllocation sa " +
            "WHERE sa.farm.activeStatus = :activeStatus " +
            "GROUP BY sa.farm " +
            "ORDER BY SUM(sa.count) DESC")
    public List<Farm> findTopFarmsByStockCountAndStatus(@Param("activeStatus") ActiveStatus activeStatus, Pageable pageable);

    public List<StockAllocation> findAllByUser(User user);

    public StockAllocation findTopByUserAndFarmOrderByDateDesc(User user,Farm farm);
    @Query("SELECT SUM(s.count) FROM StockAllocation s WHERE s.farm = :farm AND s.returnType = :returnType")
    public Integer getTotalStockAllocationByFarmAndReturnType(@Param("farm") Farm farm, @Param("returnType") ReturnType returnType);

}
