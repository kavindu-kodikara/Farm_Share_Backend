package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.WeatherScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface WeatherScoreRepo extends JpaRepository<WeatherScore,Integer> {
    @Query("SELECT ws FROM WeatherScore ws WHERE ws.farm = :farm AND FUNCTION('DATE', ws.date) = :today")
    public WeatherScore findByFarmAndDate(@Param("farm") Farm farm, @Param("today") LocalDate today);
}
