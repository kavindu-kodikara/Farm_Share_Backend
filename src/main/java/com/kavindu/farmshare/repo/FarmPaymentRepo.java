package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.FarmPayment;
import com.kavindu.farmshare.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmPaymentRepo extends JpaRepository<FarmPayment,Integer> {
    public FarmPayment findTopBySeason(Season season);
}
