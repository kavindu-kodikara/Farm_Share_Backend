package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.SoilDoc;
import com.kavindu.farmshare.entity.SoilScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface SoilScoreRepo  extends JpaRepository<SoilScore,Integer> {
    @Query("SELECT ss FROM SoilScore ss WHERE ss.soilDoc = :soilDoc AND FUNCTION('DATE', ss.date) = :today")
    public SoilScore findBySoilDocAndDate(@Param("soilDoc") SoilDoc soilDoc, @Param("today") LocalDate today);
}
