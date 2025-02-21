package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveStatusRepo  extends JpaRepository<ActiveStatus,Integer> {
    public ActiveStatus findByName(String name);
}
