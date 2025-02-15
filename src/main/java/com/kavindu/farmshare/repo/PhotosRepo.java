package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepo extends JpaRepository<Photos,Integer> {
}
