package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.ActiveStatus;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StockPriceRepo  extends JpaRepository<StockPrice,Integer> {

    public StockPrice findTopByFarmOrderByDateDesc(Farm farm);
    @Query("SELECT sp FROM StockPrice sp WHERE sp.farm = :farm AND FUNCTION('DATE', sp.date) = :today")
    public StockPrice findByFarmAndDate(@Param("farm") Farm farm, @Param("today") LocalDate today);

}
