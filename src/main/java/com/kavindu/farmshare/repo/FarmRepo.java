package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepo  extends JpaRepository<Farm,Integer> {
}
