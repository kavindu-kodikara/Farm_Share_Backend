package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.CropType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropTypeRepo  extends JpaRepository<CropType,Integer> {
}
