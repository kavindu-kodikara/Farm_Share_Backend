package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.SoilDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoilDocRepo  extends JpaRepository<SoilDoc,Integer> {
    public SoilDoc findByFarm(Farm farm);
}
