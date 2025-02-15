package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmRepo  extends JpaRepository<Farm,Integer> {
    public boolean existsByCodeName(String codeName);
    public List<Farm> findAllByUser(User user);

    @Query("SELECT f FROM Farm f WHERE NOT EXISTS (SELECT sp FROM StockPrice sp WHERE sp.farm = f AND FUNCTION('DATE', sp.date) = CURRENT_DATE)")
    public List<Farm> findFarmsWithoutStockPriceToday();
}
