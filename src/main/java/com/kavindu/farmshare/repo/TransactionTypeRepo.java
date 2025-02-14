package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepo  extends JpaRepository<TransactionType,Integer> {
}
