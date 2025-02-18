package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotosRepo extends JpaRepository<Photos,Integer> {
    public List<Photos> findAllByFarm(Farm farm);
}
