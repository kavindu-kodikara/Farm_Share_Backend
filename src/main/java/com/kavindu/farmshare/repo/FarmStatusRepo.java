package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.FarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmStatusRepo  extends JpaRepository<FarmStatus,Integer> {
    public FarmStatus findByName(String name);
}
