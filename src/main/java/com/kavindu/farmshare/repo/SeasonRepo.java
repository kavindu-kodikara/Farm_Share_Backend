package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepo  extends JpaRepository<Season,Integer> {
    public Season findByFarm(Farm farm);
}
