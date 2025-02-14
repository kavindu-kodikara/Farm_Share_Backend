package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.CropVariety;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropVarietyRepo  extends JpaRepository<CropVariety,Integer> {
}
