package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.WeatherScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherScoreRepo extends JpaRepository<WeatherScore,Integer> {
}
